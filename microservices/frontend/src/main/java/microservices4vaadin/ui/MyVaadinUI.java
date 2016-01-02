package microservices4vaadin.ui;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import microservices4vaadin.auth.AcmeUserDetails;

@Theme("valo")
@SpringUI
public class MyVaadinUI extends UI {

    private static final long serialVersionUID = -8889596293072651801L;

    @Override
    protected void init(VaadinRequest request) {
        SecurityContextImpl sci = (SecurityContextImpl) session().getAttribute("SPRING_SECURITY_CONTEXT");
        AcmeUserDetails user = (AcmeUserDetails)sci.getAuthentication().getPrincipal();

        setContent(new Label("Here's my UI"));

        Window subWindow = new Window("Welcome");
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subWindow.setContent(subContent);

        subContent.addComponent(new Label("Hello " + user.getFirstName() + " " + user.getLastName() + "!"));
        subContent.addComponent(new Button("Awlright"));

        subWindow.center();

        // Open it in the UI
        addWindow(subWindow);
    }

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

}