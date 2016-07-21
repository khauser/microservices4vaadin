package microservices4vaadin.ui;

import com.google.common.eventbus.Subscribe;
import com.vaadin.spring.server.SpringVaadinApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

import microservices4vaadin.ui.event.MyEvent.PostViewChangeEvent;
import microservices4vaadin.ui.event.MyEventBus;

public final class MyMenuItemButton extends Button {

    private static final long serialVersionUID = -318940943418526131L;

    private static final String STYLE_SELECTED = "selected";

    private final MyViewType view;

    public MyMenuItemButton(final MyViewType view, final String label) {
        this.view = view;
        setPrimaryStyleName("valo-menu-item");
        setIcon(view.getIcon());
        setCaption(label);

        MyEventBus myEventBus = (MyEventBus) SpringVaadinApplicationContext.getApplicationContext().getBean("myEventBus");
        myEventBus.register(this);
        addClickListener(e -> UI.getCurrent().getNavigator().navigateTo(view.getViewName()));
    }

    @Subscribe
    public void postViewChange(final PostViewChangeEvent event) {
        removeStyleName(STYLE_SELECTED);
        if (event.getView() == view) {
            addStyleName(STYLE_SELECTED);
        }
    }

}