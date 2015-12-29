package microservices4vaadin.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;

@Theme("valo")
@SpringUI
public class MyVaadinUI extends UI {

    private static final long serialVersionUID = -8889596293072651801L;

    @Override
    protected void init(VaadinRequest request) {
        this.setContent( new Label("Hello World!") );
    }

}