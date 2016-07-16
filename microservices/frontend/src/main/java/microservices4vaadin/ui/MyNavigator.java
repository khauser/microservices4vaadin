package microservices4vaadin.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.UI;

import microservices4vaadin.ui.event.MyEventBus;
import microservices4vaadin.ui.event.MyEvent.PostViewChangeEvent;
import microservices4vaadin.ui.event.MyEvent.BrowserResizeEvent;
import microservices4vaadin.ui.event.MyEvent.CloseOpenWindowsEvent;

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
                // Appropriate events get fired after the view is changed.
                MyEventBus.post(new PostViewChangeEvent(view));
                MyEventBus.post(new BrowserResizeEvent());
                MyEventBus.post(new CloseOpenWindowsEvent());
            }
        });
    }

}
