package microservices4vaadin.ui;

import javax.servlet.http.HttpSession;

import microservices4vaadin.auth.AcmeUserDetails;

import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("valo")
@SpringUI
public class MyVaadinUI extends UI {

    private static final long serialVersionUID = -8889596293072651801L;

    @Override
    protected void init(VaadinRequest request) {
        SecurityContextImpl sci = (SecurityContextImpl) session().getAttribute("SPRING_SECURITY_CONTEXT");
        AcmeUserDetails user = (AcmeUserDetails)sci.getAuthentication().getPrincipal();

        VerticalLayout layout = new VerticalLayout();
        //layout.setSizeFull();
        layout.setWidth("100%");
        layout.setHeight("100%");

        Label greetings = new Label(new Label("Hello <b>" + user.getFirstName() + " " + user.getLastName()
                + "</b>, now you are in the Vaadin UI"));
        greetings.setContentMode(ContentMode.HTML);
        greetings.setWidth(null);

        layout.addComponent(greetings);
        layout.setComponentAlignment(greetings, Alignment.MIDDLE_CENTER);

        setContent(layout);
    }

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }

}
