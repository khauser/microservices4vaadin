package microservices4vaadin.frontend.ui;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import microservices4vaadin.frontend.rest.resource.dto.UserServiceUserDTO;

@SpringView(name = HomeView.VIEW_NAME)
public class HomeView extends Panel implements View {

    private static final long serialVersionUID = -7001285092564194997L;

    public static final String VIEW_NAME = "";

    private boolean initialized = false;

    private VerticalLayout layout;


    @PostConstruct
    void init() {

        layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);

        setSizeFull();
    }

    @Override
    public void enter(ViewChangeEvent event) {
        if (!initialized) {
            layout.addStyleName("dashboard-view");

            layout.addComponent(buildHeader());
            layout.addComponent(buildBody());

            setContent(layout);
            Responsive.makeResponsive(layout);
            initialized = true;
        }
    }

    private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);

        Responsive.makeResponsive(header);

        Label title = new Label("Welcome");
        title.setSizeFull();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);
        return header;
    }

    private Component buildBody() {
        VerticalLayout bodyLayout = new VerticalLayout();
        bodyLayout.setSizeFull();

        UserServiceUserDTO user = getCurrentUser();

        Label greetings = new Label("Hello <b>" + user.getFirstName() + " " + user.getLastName()
        + "</b>, now you are in the Vaadin UI.");
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

    public UserServiceUserDTO getCurrentUser() {
        return (UserServiceUserDTO) VaadinSession.getCurrent().getAttribute(
                UserServiceUserDTO.class.getName());
    }

}
