package microservices4vaadin.frontend.ui;

import org.springframework.context.ApplicationEventPublisher;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.server.SpringVaadinServletService;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

import microservices4vaadin.frontend.ui.event.MyEvent.BrowserResizeEvent;
import microservices4vaadin.frontend.ui.event.MyEvent.CloseOpenWindowsEvent;
import microservices4vaadin.frontend.ui.event.MyEvent.PostViewChangeEvent;

public class MyNavigator extends Navigator {

    private static final long serialVersionUID = 3705219716729202395L;

    public MyNavigator(final ComponentContainer container) {
        super(UI.getCurrent(), container);

        initViewChangeListener();

    }

    private void initViewChangeListener() {
        addViewChangeListener(new ViewChangeListener() {

            private static final long serialVersionUID = 4677058989845528128L;

            @Override
            public boolean beforeViewChange(final ViewChangeEvent event) {
                // Since there's no conditions in switching between the views
                // we can always return true.
                return true;
            }

            @Override
            public void afterViewChange(final ViewChangeEvent event) {
                MyViewType view = MyViewType.getByViewName(event
                        .getViewName());

                UI ui = UI.getCurrent();

                ApplicationEventPublisher eventPublisher = (ApplicationEventPublisher) ((SpringVaadinServletService) ui.getSession().getService()).getWebApplicationContext();
                // Appropriate events get fired after the view is changed.
                eventPublisher.publishEvent(new PostViewChangeEvent(MyVaadinUI.getCurrent(), view));
                eventPublisher.publishEvent(new BrowserResizeEvent(MyVaadinUI.getCurrent()));
                eventPublisher.publishEvent(new CloseOpenWindowsEvent(MyVaadinUI.getCurrent()));
            }
        });
    }

}
