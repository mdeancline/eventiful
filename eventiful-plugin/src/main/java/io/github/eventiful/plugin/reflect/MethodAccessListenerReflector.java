package io.github.eventiful.plugin.reflect;

import com.esotericsoftware.reflectasm.MethodAccess;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class MethodAccessListenerReflector implements ListenerReflector {
    @Override
    public Collection<ListenerMethod<?>> getMethods(final Listener listener) {
        final MethodAccess access = MethodAccess.get(listener.getClass());
        final Method[] declaredMethods = listener.getClass().getMethods();
        final List<ListenerMethod<?>> methods = new ArrayList<>();

        for (int i = 0; i < declaredMethods.length; i++)
            if (isHandlerMethod(declaredMethods[i]))
                methods.add(new AccessedMethod<>(listener, declaredMethods[i].getAnnotation(EventHandler.class), access, i));

        return Collections.unmodifiableCollection(methods);
    }

    private boolean isHandlerMethod(final Method declaredMethod) {
        final Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
        return parameterTypes.length == 1
                && declaredMethod.isAnnotationPresent(EventHandler.class)
                && Event.class.isAssignableFrom(parameterTypes[0]);
    }

    private static final class AccessedMethod<T extends Event> implements ListenerMethod<T> {
        private final Listener listener;
        private final EventHandler annotation;
        private final MethodAccess access;
        private final int accessIndex;

        private AccessedMethod(final Listener listener, final EventHandler annotation, final MethodAccess access, final int accessIndex) {
            this.listener = listener;
            this.annotation = annotation;
            this.access = access;
            this.accessIndex = accessIndex;
        }

        @Override
        public void invoke(final T event) {
            final Class<?>[] singletonParameterType = access.getParameterTypes()[accessIndex];
            final String methodName = access.getMethodNames()[accessIndex];
            access.invoke(listener, methodName, singletonParameterType, event);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Class<T> getParameterType() {
            return access.getParameterTypes()[accessIndex][0];
        }

        @Override
        public EventHandler getAnnotation() {
            return annotation;
        }
    }
}
