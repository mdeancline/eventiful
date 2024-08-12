package io.github.eventiful.plugin;

import io.github.classgraph.ClassGraph;
import io.github.eventiful.api.EventBus;
import io.github.eventiful.api.listener.decorator.IdentityEventInclusion;
import io.github.eventiful.plugin.player.PlayerJoinListener;
import io.github.eventiful.plugin.player.PlayerQuitListener;
import io.github.eventiful.plugin.player.PlayerStatisticIncrementListener;
import io.github.eventiful.plugin.player.UntypedStatisticRepository;
import io.github.eventiful.plugin.player.movement.PlayerMoveEventCaller;
import io.github.eventiful.plugin.player.movement.PlayerMoveListener;
import io.github.eventiful.plugin.player.movement.context.*;
import io.github.eventiful.plugin.reflect.*;
import io.github.eventiful.plugin.registration.EventTokenProvider;
import io.github.eventiful.plugin.registration.SimpleEventTokenProvider;
import io.github.eventiful.plugin.scanner.CacheableClassScanner;
import io.github.eventiful.plugin.scanner.ClassGraphScanner;
import io.github.eventiful.plugin.scanner.ClassScanner;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
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

public class EventifulPlugin extends JavaPlugin {
    private final ClassGraphScanner classGraphScanner = new ClassGraphScanner(new ClassGraph().enableAllInfo());
    private final ReflectionAccess reflectionAccess = createReflectionAccess();
    private final ServerEventBus eventBus = createServerEventBus();
    private final ListenerRegistry listenerRegistry = new ListenerRegistry(createListenerReflector(), eventBus);

    private ServerEventBus createServerEventBus() {
        final ClassScanner classScanner = new CacheableClassScanner(classGraphScanner);
        final EventLogger logger = new EventLogger(getLogger());
        final EventTokenProvider tokenProvider = new SimpleEventTokenProvider();
        return new EventBusImpl(classScanner, logger, tokenProvider, this);
    }

    private ReflectionAccess createReflectionAccess() {
        return new UnsafeReflectionAccess();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onLoad() {
        final EventBusAdapter eventBusAdapter = new EventBusAdapter(eventBus, this);
        final HandlerListInjector handlersInjector = new HandlerListInjector(reflectionAccess);
        final RegisteredListener[] listenerProxies = new RegisteredListener[]{eventBusAdapter};

        classGraphScanner.scanSubtypes(Event.class, subtype -> {
            if (!Modifier.isAbstract(subtype.getModifiers())) {
                final Class<? extends Event> eventSubtype = (Class<? extends Event>) subtype;
                final HandlerListProxy proxy = new HandlerListProxy(eventSubtype, listenerRegistry, listenerProxies);
                handlersInjector.inject(proxy, eventSubtype);
            }
        });

        final UntypedStatisticRepository repository = new UntypedStatisticRepository();
        final PlayerMoveEventCaller resolver = new PlayerMoveEventCaller(eventBus,
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
        eventBus.register(PlayerMoveEvent.class, new IdentityEventInclusion<>(new PlayerMoveListener(resolver), PlayerMoveEvent.class));

        Bukkit.getServicesManager().register(EventBus.class, eventBus, this, ServicePriority.Normal);
    }

    @Override
    public void onEnable() {
        for (final Plugin plugin : getServer().getPluginManager().getPlugins()) {
            for (final Field field : reflectionAccess.getAllDeclaringFields(plugin)) {
                if (field.getType() == PluginLoader.class) {
                    final PluginLoader source = (PluginLoader) reflectionAccess.getObject(field, plugin);
                    reflectionAccess.setObject(field, new PluginLoaderProxy(source, listenerRegistry), plugin);
                    break;
                }
            }
        }
    }

    private ListenerReflector createListenerReflector() {
        final ListenerReflector methodAccessReflector = new MethodAccessListenerReflector();
        final ListenerReflector cancellableReflector = new CancellableListenerReflector(methodAccessReflector);
        return new CacheableListenerReflector(cancellableReflector);
    }
}
