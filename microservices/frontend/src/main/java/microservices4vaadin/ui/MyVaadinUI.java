package microservices4vaadin.ui;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import org.springframework.web.context.ContextLoaderListener;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@Theme("valo")
@SpringUI
public class MyVaadinUI extends UI {

    private static final long serialVersionUID = -8889596293072651801L;

    @WebServlet(value = "/*", asyncSupported = true)
    public static class Servlet extends SpringVaadinServlet {
        private static final long serialVersionUID = -5069755694362893409L;
    }

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Override
    protected void init(VaadinRequest request) {
        this.setContent( new Label("Hello World!") );
    }

}