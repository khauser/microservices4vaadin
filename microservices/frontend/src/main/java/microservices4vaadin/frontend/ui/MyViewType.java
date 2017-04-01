package microservices4vaadin.frontend.ui;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.Resource;

public enum MyViewType {
    HOME(HomeView.VIEW_NAME, "Home", HomeView.class, VaadinIcons.HOME, true)
    , BOOK(BookView.VIEW_NAME, "Book", BookView.class, VaadinIcons.BOOK, true);

    private final String viewName;
    private final String viewLabel;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private MyViewType(final String viewName
            , final String viewLabel
            , final Class<? extends View> viewClass
            , final Resource icon
            , final boolean stateful) {
        this.viewName = viewName;
        this.viewLabel = viewLabel;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public String getViewLabel() {
        return viewLabel;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static MyViewType getByViewName(final String viewName) {
        MyViewType result = null;
        for (MyViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
