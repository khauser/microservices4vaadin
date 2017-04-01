package microservices4vaadin.frontend.ui;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
//import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;

import microservices4vaadin.frontend.rest.resource.dto.UserServiceUserDTO;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@UIScope
@SpringComponent
public class MainView extends VerticalLayout {

    private static final long serialVersionUID = -6726502572397504376L;

    @Autowired
    private SpringViewProvider viewProvider;

    @Autowired
    private MyMenu myMenu;

    @Autowired
    private ProfileWindow profileWindow;

    private MenuItem settingsItem;

    private ComponentContainer content;


    @PostConstruct
    void init() {
        setSizeFull();
        addStyleName("mainview");
    }

    private Component buildHeader() {
        HorizontalLayout headerLayout = new HorizontalLayout();
        headerLayout.addComponent(myMenu);
        headerLayout.setComponentAlignment(myMenu, Alignment.MIDDLE_LEFT);

        Label logo = new Label("microservices4vaadin",
                ContentMode.HTML);
        logo.setSizeUndefined();
        headerLayout.addComponent(logo);
        headerLayout.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        headerLayout.addStyleName("valo-menu-title");
        headerLayout.setWidth("100%");
        headerLayout.setMargin(false);

        Component userMenu = buildUserMenu();
        headerLayout.addComponent(userMenu);
        //headerLayout.setComponentAlignment(userMenu, Alignment.MIDDLE_RIGHT);


        return headerLayout;
    }

    private Component buildUserMenu() {
        UserServiceUserDTO user = getCurrentUser();

        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        settingsItem = settings.addItem("", new ThemeResource(
                "images/profile-pic-300px.jpg"), null);
        settingsItem.setText(user.getFirstName() + " " + user.getLastName());

        settingsItem.addItem("Profile", new Command() {
            private static final long serialVersionUID = -3817204076897480538L;

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                profileWindow.open(getCurrentUser());
            }
        });
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", new Command() {
            private static final long serialVersionUID = -728291901908708727L;

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                VaadinSession.getCurrent().close();
                UI.getCurrent().getPage().setLocation("/logout");
                //DashboardEventBus.post(new UserLoggedOutEvent());
            }
        });
        return settings;
    }

    public void enter() {
        addComponent(buildHeader());

        content = new CssLayout();
        content.addStyleName("view-content");
        content.setSizeFull();
        addComponent(content);
        setExpandRatio(content, 1.0f);

        myMenu.enter();
        MyNavigator navigator = new MyNavigator(content);
//        //navigator.navigateTo(HomeView.VIEW_NAME);
        navigator.addProvider(viewProvider);
    }

    public UserServiceUserDTO getCurrentUser() {
        return (UserServiceUserDTO) VaadinSession.getCurrent().getAttribute(
                UserServiceUserDTO.class.getName());
    }

}
