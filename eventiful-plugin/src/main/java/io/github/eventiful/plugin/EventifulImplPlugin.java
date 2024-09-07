package io.github.eventiful.plugin;

import io.github.classgraph.ClassGraph;
import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.event.server.ServerLoadEvent;
import io.github.eventiful.api.exception.EventRegistrationException;
import io.github.eventiful.api.listener.decorator.IdentityEventInclusion;
import io.github.eventiful.plugin.event.*;
import io.github.eventiful.plugin.hook.PluginHookPool;
import io.github.eventiful.plugin.hook.model.ProtocolLibHook;
import io.github.eventiful.plugin.reflect.*;
import io.github.eventiful.plugin.registration.EventTokenProvider;
import io.github.eventiful.plugin.registration.SimpleEventTokenProvider;
import io.github.eventiful.plugin.scanner.CacheableClassScanner;
import io.github.eventiful.plugin.scanner.ClassGraphScanner;
import io.github.eventiful.plugin.scanner.ClassScanner;
import io.github.eventiful.plugin.util.EquipmentSlotResolver;
import io.github.eventiful.plugin.util.ItemDamageCalculator;
import io.github.eventiful.plugin.util.ItemDamageSupport;
import net.insprill.spigotutils.MinecraftVersion;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.RegisteredListener;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class EventifulImplPlugin extends JavaPlugin {
    private static final long SERVER_LOAD_FINISH_DELAY_TICKS = 1L;
    private static final int METRICS_SERVICE_ID = 23270;

    private final EventLogger logger = new EventLogger(getLogger());
    private final ClassScanner classScanner = new ClassGraphScanner(new ClassGraph().enableAllInfo());
    private final ReflectionAccess reflectionAccess = createReflectionAccess();
    private final ServerEventBus eventBus = createServerEventBus();
    private final ListenerRegistry listenerRegistry = new ListenerRegistry(createListenerReflector(), eventBus);
    private final PluginHookPool hookPool = new PluginHookPool(logger);
    private Metrics metrics;

    private ServerEventBus createServerEventBus() {
        final ClassScanner cacheableClassScanner = new CacheableClassScanner(this.classScanner);
        final EventTokenProvider tokenProvider = new SimpleEventTokenProvider();
        return new EventBusImpl(cacheableClassScanner, logger, tokenProvider, this);
    }

    private ReflectionAccess createReflectionAccess() {
        return new UnsafeReflectionAccess();
    }

    private ListenerReflector createListenerReflector() {
        final ListenerReflector methodAccessReflector = new MethodAccessListenerReflector();
        final ListenerReflector cancellableReflector = new CancellableListenerReflector(methodAccessReflector);
        return new CacheableListenerReflector(cancellableReflector);
    }

    @Override
    public void onLoad() {
        if (!MinecraftVersion.isAtLeast(MinecraftVersion.v1_8_0))
            logger.logWarning("You are using an unsupported Minecraft server version with this plugin. " +
                    "This version has not been tested for compatibility, so proceed with caution and use at your own risk!");

        loadComponents();
    }

    @SuppressWarnings("unchecked")
    private void loadComponents() {
        final EventBusAdapter eventBusAdapter = new EventBusAdapter(eventBus, this);
        final HandlerListInjector handlersInjector = new HandlerListInjector(reflectionAccess);
        final RegisteredListener[] listenerProxies = new RegisteredListener[]{eventBusAdapter};

        classScanner.scanSubtypes(Event.class, subtype -> {
            if (!Modifier.isAbstract(subtype.getModifiers())) {
                final Class<? extends Event> eventSubtype = (Class<? extends Event>) subtype;
                final HandlerListProxy proxy = new HandlerListProxy(eventSubtype, listenerRegistry, listenerProxies);
                handlersInjector.inject(proxy, eventSubtype);
            }
        });

        final UntypedStatisticRepository repository = new UntypedStatisticRepository();
        final PlayerMoveEventCaller playerMoveEventCaller = new PlayerMoveEventCaller(eventBus,
                new PlayerJumpContext(repository),
                new PlayerRotateContext(),
                new PlayerSprintContext(),
                new PlayerSneakContext(),
                new PlayerWalkContext());

        final PlayerJoinListener joinListener = new PlayerJoinListener();
        final PlayerQuitListener quitListener = new PlayerQuitListener();
        joinListener.addAuditor(repository);
        quitListener.addAuditor(repository);

        eventBus.register(PlayerJoinEvent.class, joinListener);
        eventBus.register(PlayerQuitEvent.class, quitListener);
        eventBus.register(PlayerStatisticIncrementEvent.class, new PlayerStatisticIncrementListener(repository));
        eventBus.register(PlayerMoveEvent.class, new IdentityEventInclusion<>(playerMoveEventCaller, PlayerMoveEvent.class));

        final EquipmentSlotResolver slotResolver = new EquipmentSlotResolver();
        final ItemDamageCalculator itemDamageCalculator = ItemDamageSupport.newItemDamageCalculator();
        eventBus.register(EntityDamageEvent.class, new EntityArmorDamageListener(eventBus, slotResolver, itemDamageCalculator));

        Bukkit.getServicesManager().register(EventBus.class, eventBus, this, ServicePriority.Normal);
    }

    @Override
    public void onEnable() {
        metrics = new Metrics(this, METRICS_SERVICE_ID);

        for (final Plugin plugin : getServer().getPluginManager().getPlugins()) {
            for (final Field field : reflectionAccess.getAllDeclaringFields(plugin.getClass())) {
                if (field.getType() == PluginLoader.class) {
                    final PluginLoader source = (PluginLoader) reflectionAccess.getObject(field, plugin);
                    reflectionAccess.setObject(field, new PluginLoaderProxy(source, listenerRegistry), plugin);
                    break;
                }
            }
        }

        dispatchServerLoadEvent();

        hookPool.register(new ProtocolLibHook(eventBus, this));
        hookPool.setup();
    }

    @Override
    public void onDisable() {
        metrics.shutdown();
        metrics = null;
    }

    private void dispatchServerLoadEvent () {
        final ServerLoadEvent.Type loadType = getReloadCount() == 0
                ? ServerLoadEvent.Type.STARTUP
                : ServerLoadEvent.Type.RELOAD;

        eventBus.dispatch(new ServerLoadEvent(loadType));

        Bukkit.getScheduler().runTaskLater(this, ()
                -> eventBus.dispatch(new ServerLoadEvent(ServerLoadEvent.Type.FINISHED)), SERVER_LOAD_FINISH_DELAY_TICKS);
    }

    private int getReloadCount() {
        try {
            return (int) reflectionAccess.getObject(getServer().getClass().getDeclaredField("reloadCount"), getServer());
        } catch (final NoSuchFieldException e) {
            throw new EventRegistrationException(e);
        }
    }
}