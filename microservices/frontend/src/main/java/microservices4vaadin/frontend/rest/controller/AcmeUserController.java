package microservices4vaadin.frontend.rest.controller;

import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestOperations;

import lombok.extern.slf4j.Slf4j;
import microservices4vaadin.frontend.rest.resource.update.CredentialUpdateResource;

@Controller
@Slf4j
public class AcmeUserController {

    public final static String USER_REST_URL = "/uaa/user";

    @Autowired
    private RestOperations restTemplate;

    @Autowired
    private RestUtils restUtils;

    @Autowired
    private HttpServletRequest request;

    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer";
    public static final String BASIC = "Basic";

    public void updateCredentials(String email, String newPassword, String oldPassword) {
        log.debug("Setting up REST call to update user credentials");
        CredentialUpdateResource resource = new CredentialUpdateResource();
        resource.setEmail(email);
        resource.setNewPassword(newPassword);
        resource.setOldPassword(oldPassword);

        HttpHeaders headers = new HttpHeaders();
        headers.put("COOKIE", Arrays.asList("JSESSIONID=" + getSessionId() + "; Path=/; HttpOnly"));

        HttpEntity<CredentialUpdateResource> entity = new HttpEntity<>(resource,headers);


        restTemplate.exchange(restUtils.getServiceUrl("authserver") + USER_REST_URL
         , HttpMethod.PATCH, entity,
         CredentialUpdateResource.class);
    }

    public String getSessionId() {

        Cookie[] cookies = request.getCookies();

        for (Cookie c:cookies)
        {
            log.debug(String.format("Cookie: %s, %s, domain: %s",c.getName(), c.getValue(),c.getDomain()));
            if (c.getName().equals("JSESSIONID"))
                return c.getValue();
        }
        return null;
    }

}
