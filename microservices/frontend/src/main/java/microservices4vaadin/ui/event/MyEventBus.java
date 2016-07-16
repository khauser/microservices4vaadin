package microservices4vaadin.ui.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

import microservices4vaadin.ui.MyVaadinUI;

/**
 * A simple wrapper for Guava event bus. Defines static convenience methods for
 * relevant actions.
 */
public class MyEventBus implements SubscriberExceptionHandler {

    private final EventBus eventBus = new EventBus(this);

    public static void post(final Object event) {
        MyVaadinUI.getMyEventbus().eventBus.post(event);
    }

    public static void register(final Object object) {
        MyVaadinUI.getMyEventbus().eventBus.register(object);
    }

    public static void unregister(final Object object) {
        MyVaadinUI.getMyEventbus().eventBus.unregister(object);
    }

    @Override
    public final void handleException(final Throwable exception,
            final SubscriberExceptionContext context) {
        exception.printStackTrace();
    }
}
