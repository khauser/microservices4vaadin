package microservices4vaadin.frontend.ui;

import javax.servlet.ServletException;

import org.springframework.stereotype.Component;

import com.vaadin.spring.server.SpringVaadinServlet;

import lombok.extern.slf4j.Slf4j;

@Component("vaadinServlet")
@Slf4j
public class MyVaadinUIServlet extends SpringVaadinServlet {

    private static final long serialVersionUID = 3407235182832472556L;

    @Override
    protected void servletInitialized() throws ServletException {
        super.servletInitialized();
        getService().addSessionInitListener(new MySessionInitListener());
        log.debug("MyVaadinUIServlet initialized");
    }

}
