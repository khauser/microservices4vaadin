package microservices4vaadin.edge.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    //public final static String DEFAULT_TARGET_URL = "https://localhost:8443";

    @Override
    public void onLogoutSuccess( HttpServletRequest request,
        HttpServletResponse response, Authentication authentication )
        throws IOException, ServletException {
        SimpleUrlLogoutSuccessHandler logoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
        //TODO: indicate logout and present special logout site
        logoutSuccessHandler.setDefaultTargetUrl( "/" );
        logoutSuccessHandler.onLogoutSuccess( request, response, authentication );
    }

}
