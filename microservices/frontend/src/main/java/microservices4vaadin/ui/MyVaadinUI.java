package microservices4vaadin.ui;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.jdal.annotation.SerializableProxy;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vaadin.annotations.Theme;
import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import microservices4vaadin.auth.AcmeUserDetails;
import microservices4vaadin.rest.controller.UserServiceController;
import microservices4vaadin.rest.resource.dto.UserServiceUserDTO;
import microservices4vaadin.ui.event.MyEvent.CloseOpenWindowsEvent;

@Theme("microservices4vaadin")
@SpringUI
public class MyVaadinUI extends UI {

    private static final long serialVersionUID = -8889596293072651801L;

    @Autowired
    private MainView mainView;

    @Autowired
    @SerializableProxy
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    @SerializableProxy
    private UserServiceController userServiceController;

    @Override
    protected void init(VaadinRequest request) {
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        updateContent();

        VaadinSession.getCurrent().addBootstrapListener(new BootstrapListener() {

            private static final long serialVersionUID = -4273381397632294262L;

            @Override
            public void modifyBootstrapPage(final BootstrapPageResponse response) {
                final Element head = response.getDocument().head();
                head.appendElement("meta")
                        .attr("name", "viewport")
                        .attr("content",
                                "width=device-width, initial-scale=1.0");
                head.appendElement("meta")
                        .attr("name", "apple-mobile-web-app-capable")
                        .attr("content", "yes");
                head.appendElement("meta")
                        .attr("name", "apple-mobile-web-app-status-bar-style")
                        .attr("content", "black-translucent");
            }

            @Override
            public void modifyBootstrapFragment(
                    final BootstrapFragmentResponse response) {
            }
        });
    }

    private void updateContent() {
        SecurityContextImpl sci = (SecurityContextImpl) session().getAttribute("SPRING_SECURITY_CONTEXT");
        AcmeUserDetails user = (AcmeUserDetails)sci.getAuthentication().getPrincipal();

        UserServiceUserDTO userServiceUser = userServiceController.findOne(user.getItemId());
        userServiceUser.setAcmeUser(user);

        VaadinSession.getCurrent().setAttribute(UserServiceUserDTO.class.getName(), userServiceUser);
        VaadinSession.getCurrent().setLocale(Locale.forLanguageTag(userServiceUser.getLanguage()));

        VaadinSession.getCurrent().setAttribute(AcmeUserDetails.class.getName(), user);

        mainView.enter();
        setContent(mainView);

        String navigatorState = getNavigator().getState();
        if (navigatorState == null || navigatorState.isEmpty())
            getNavigator().navigateTo(HomeView.VIEW_NAME);
        else
            getNavigator().navigateTo(navigatorState);
    }


    @EventListener
    public void closeOpenWindows(final CloseOpenWindowsEvent event) {
        for (Window window : getWindows()) {
            window.close();
        }
    }

    public static HttpSession session() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest().getSession(true); // true == allow create
    }


}
