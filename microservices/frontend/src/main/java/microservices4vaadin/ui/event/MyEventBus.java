package microservices4vaadin.ui.event;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;

@Service
public class MyEventBus implements SubscriberExceptionHandler {

    private EventBus bus;

    @PostConstruct
    public void init() {
        bus = new EventBus();
    }

    public void post(final Object event) {
        bus.post(event);
    }

    public  void register(final Object object) {
        bus.register(object);
    }

    public void unregister(final Object object) {
        bus.unregister(object);
    }

    @Override
    public void handleException(final Throwable exception,
            final SubscriberExceptionContext context) {
        exception.printStackTrace();
    }

}
