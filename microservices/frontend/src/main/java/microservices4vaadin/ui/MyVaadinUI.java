package microservices4vaadin.ui;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import microservices4vaadin.auth.AcmeUserDetails;

@Theme("microservices4vaadin")
@SpringUI
public class MyVaadinUI extends UI {

    private static final long serialVersionUID = -8889596293072651801L;

    private AcmeUserDetails user;

    private MenuItem settingsItem;

    @Override
    protected void init(VaadinRequest request) {
        SecurityContextImpl sci = (SecurityContextImpl) session().getAttribute("SPRING_SECURITY_CONTEXT");
        user = (AcmeUserDetails)sci.getAuthentication().getPrincipal();

        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.setHeight("100%");
        layout.setSizeFull();

        layout.addComponent(buildHeader());
        layout.addComponent(buildBody());

        setContent(layout);
    }

    private Component buildHeader() {
        Label logo = new Label("microservices4vaadin",
                ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout headerLayout = new HorizontalLayout(logo);
        headerLayout.setComponentAlignment(logo, Alignment.MIDDLE_LEFT);
        headerLayout.addStyleName("valo-menu-title");
        headerLayout.setWidth("100%");
        headerLayout.setMargin(false);

        Component userMenu = buildUserMenu();
        headerLayout.addComponent(userMenu);
        //headerLayout.setComponentAlignment(userMenu, Alignment.MIDDLE_RIGHT);


        return headerLayout;
    }

    private Component buildBody() {
        VerticalLayout bodyLayout = new VerticalLayout();
        bodyLayout.setSizeFull();

        Label greetings = new Label(new Label("Hello <b>" + user.getFirstName() + " " + user.getLastName()
        + "</b>, now you are in the Vaadin UI."));
        greetings.setContentMode(ContentMode.HTML);
        greetings.setWidth(null);

        bodyLayout.addComponent(greetings);
        bodyLayout.setComponentAlignment(greetings, Alignment.BOTTOM_CENTER);

        Button testButton = new Button("TestButton");
        bodyLayout.addComponent(testButton);
        bodyLayout.setComponentAlignment(testButton, Alignment.TOP_CENTER);
        testButton.addClickListener(e -> {
            Window window = new Window("TestWindow");
            UI.getCurrent().addWindow(window);
            window.focus();
            window.center();
        });
        return bodyLayout;
    }

    private Component buildUserMenu() {
        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");
        settingsItem = settings.addItem("", new ThemeResource(
                "images/profile-pic-300px.jpg"), null);
        settingsItem.setText(user.getFirstName() + " " + user.getLastName());
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

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

}
