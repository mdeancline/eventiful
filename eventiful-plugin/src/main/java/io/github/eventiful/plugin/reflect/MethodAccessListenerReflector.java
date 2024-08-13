package io.github.eventiful.plugin.reflect;

import com.esotericsoftware.reflectasm.MethodAccess;
import lombok.AllArgsConstructor;
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

    @AllArgsConstructor
    private static final class AccessedMethod<T extends Event> implements ListenerMethod<T> {
        private final Listener listener;
        private final EventHandler annotation;
        private final MethodAccess access;
        private final int eventParameterIndex;

        @Override
        public void invoke(final T event) {
            final Class<?>[] singletonParameterType = access.getParameterTypes()[eventParameterIndex];
            final String methodName = access.getMethodNames()[eventParameterIndex];
            access.invoke(listener, methodName, singletonParameterType, event);
        }

        @SuppressWarnings("unchecked")
        @Override
        public Class<T> getParameterType() {
            return access.getParameterTypes()[eventParameterIndex][0];
        }

        @Override
        public EventHandler getAnnotation() {
            return annotation;
        }
    }
}
