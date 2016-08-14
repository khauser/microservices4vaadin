package microservices4vaadin.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestOperations;

import microservices4vaadin.rest.controller.RestUtils;
import microservices4vaadin.rest.resource.update.CredentialUpdateResource;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class AcmeUserController {

    public final static String USER_REST_URL = "/user";

    @Autowired
    private RestOperations restTemplate;

    @Autowired
    private RestUtils restUtils;

    public void updateCredentials(String email, String newPassword, String oldPassword) {
        log.debug("Setting up REST call to update user credentials");
        CredentialUpdateResource resource = new CredentialUpdateResource();
        resource.setEmail(email);
        resource.setNewPassword(newPassword);
        resource.setOldPassword(oldPassword);

        restTemplate.exchange(restUtils.getServiceUrl("authserver") + USER_REST_URL
         , HttpMethod.PATCH, new HttpEntity<CredentialUpdateResource>(resource),
         CredentialUpdateResource.class);
    }

}
