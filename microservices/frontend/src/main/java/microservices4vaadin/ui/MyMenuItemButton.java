package microservices4vaadin.ui;

import org.springframework.context.event.EventListener;

import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

import microservices4vaadin.ui.event.MyEvent.PostViewChangeEvent;

public final class MyMenuItemButton extends Button {

    private static final long serialVersionUID = -318940943418526131L;

    private static final String STYLE_SELECTED = "selected";

    private final MyViewType view;

    public MyMenuItemButton(final MyViewType view, final String label) {
        this.view = view;
        setPrimaryStyleName("valo-menu-item");
        setIcon(view.getIcon());
        setCaption(label);

        addClickListener(e -> UI.getCurrent().getNavigator().navigateTo(view.getViewName()));
    }

    @EventListener
    public void postViewChange(final PostViewChangeEvent event) {
        removeStyleName(STYLE_SELECTED);
        if (event.getView() == view) {
            addStyleName(STYLE_SELECTED);
        }
    }

}