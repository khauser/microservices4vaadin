package microservices4vaadin.ui;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import microservices4vaadin.auth.AcmeUserDetails;
import microservices4vaadin.ui.event.MyEvent.CloseOpenWindowsEvent;
import microservices4vaadin.ui.event.MyEventBus;

@Theme("microservices4vaadin")
@SpringUI
public class MyVaadinUI extends UI {

    private static final long serialVersionUID = -8889596293072651801L;

    private final transient MyEventBus myEventbus = new MyEventBus();

    @Autowired
    private MainView mainView;

    @Override
    protected void init(VaadinRequest request) {
        MyEventBus.register(this);
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        updateContent();
    }

    private void updateContent() {
        SecurityContextImpl sci = (SecurityContextImpl) session().getAttribute("SPRING_SECURITY_CONTEXT");
        AcmeUserDetails user = (AcmeUserDetails)sci.getAuthentication().getPrincipal();

        VaadinSession.getCurrent().setAttribute(AcmeUserDetails.class.getName(), user);

        mainView.enter();
        setContent(mainView);

        String navigatorState = getNavigator().getState();
        if (navigatorState == null || navigatorState.isEmpty())
            getNavigator().navigateTo(HomeView.VIEW_NAME);
        else
            getNavigator().navigateTo(navigatorState);
    }


    @Subscribe
    public void closeOpenWindows(final CloseOpenWindowsEvent event) {
        for (Window window : getWindows()) {
            window.close();
        }
    }

    public static MyEventBus getMyEventbus() {
        return ((MyVaadinUI) getCurrent()).myEventbus;
    }

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }


}
