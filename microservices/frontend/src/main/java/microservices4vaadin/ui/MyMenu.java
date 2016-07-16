package microservices4vaadin.ui;

import javax.annotation.PostConstruct;

import com.google.common.eventbus.Subscribe;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.themes.ValoTheme;

import microservices4vaadin.ui.event.MyEvent.PostViewChangeEvent;
import microservices4vaadin.ui.event.MyEventBus;

//import lessoria.rest.resource.dto.AccountDTO;
//import lessoria.rest.resource.dto.UserDTO;
//import lessoria.ui.event.LessorEventBus;
//import lessoria.ui.event.LessorEvent.PostViewChangeEvent;
//import lessoria.ui.event.LessorEvent.ProfileUpdatedEvent;
//import lessoria.ui.event.LessorEvent.UserLoggedOutEvent;

/**
 * A responsive menu component providing user information and the controls for
 * primary navigation between the views.
 */
@UIScope
@SpringComponent
public final class MyMenu extends CustomComponent {

    private static final long serialVersionUID = 4389881876097842074L;

    public static final String ID = "lessor-menu";
    public static final String REPORTS_BADGE_ID = "dashboard-menu-reports-badge";
    public static final String NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
    private static final String STYLE_VISIBLE = "valo-menu-visible";

//    private MenuItem settingsItem;

//    @Autowired
//    private transient ReloadableResourceBundleMessageSource messageSource;

//    @Autowired
//    private ProfileWindow profileWindow;
//
//    private AccountDTO account;

    @PostConstruct
    void init() {
        setPrimaryStyleName("valo-menu");
        setId(ID);
        setSizeUndefined();
    }

    public void enter() {
//        account = (AccountDTO) VaadinSession.getCurrent().getAttribute(
//                AccountDTO.class.getName());
//
        // There's only one DashboardMenu per UI so this doesn't need to be
        // unregistered from the UI-scoped DashboardEventBus.
        MyEventBus.register(this);

        setCompositionRoot(buildContent());
    }

    private Component buildContent() {
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());

        return menuContent;
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", e -> {
            if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
                getCompositionRoot().removeStyleName(STYLE_VISIBLE);
            } else {
                getCompositionRoot().addStyleName(STYLE_VISIBLE);
            }
        });

        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");
        menuItemsLayout.setHeight(100.0f, Unit.PERCENTAGE);

        for (final MyViewType view : MyViewType.values()) {
            Component menuItemComponent = new MyMenuItemButton(view,
                    view.getViewLabel());
            menuItemsLayout.addComponent(menuItemComponent);
        }

        return menuItemsLayout;

    }

    @Subscribe
    public void postViewChange(final PostViewChangeEvent event) {
        // After a successful view change the menu can be hidden in mobile view.
        getCompositionRoot().removeStyleName(STYLE_VISIBLE);
    }

}
