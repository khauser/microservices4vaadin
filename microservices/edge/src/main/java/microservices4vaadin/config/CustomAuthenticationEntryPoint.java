package microservices4vaadin.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final String HOME_PAGE = "/";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
     AuthenticationException authException ) throws IOException{
        String method = request.getMethod();
        String servletPath = request.getServletPath();

        if (method.equals("GET") && servletPath.equals("/uaa/login"))
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        else
            response.sendRedirect(HOME_PAGE);
    }

}
