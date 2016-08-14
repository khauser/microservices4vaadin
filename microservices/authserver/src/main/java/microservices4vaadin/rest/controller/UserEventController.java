package microservices4vaadin.rest.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestOperations;

import lombok.extern.slf4j.Slf4j;
import microservices4vaadin.rest.controller.util.RestUtils;
import microservices4vaadin.service.Registration;
import microservices4vaadin.userevents.UserCreatedEvent;

@Controller
@Slf4j
public class UserEventController {

    public final static String USER_EVENT_REST_URL = "/user";

    @Autowired
    private RestOperations restTemplate;

    @Autowired
    private RestUtils restUtils;

    public void publishCreatedUserEvent(Long id, Registration registration) {
        log.debug("Setting up REST call to publish an CreatedUserEvent");

        UserCreatedEvent userCreatedEvent = new UserCreatedEvent();
        userCreatedEvent.setId(id);
        userCreatedEvent.setEmail(registration.getEmail());
        userCreatedEvent.setFirstName(registration.getFirstName());
        userCreatedEvent.setLastName(registration.getLastName());

        RequestEntity<UserCreatedEvent> request = RequestEntity.post(URI.create(restUtils.getServiceUrl("eventstore") + USER_EVENT_REST_URL + "/createdEvent"))
                //.accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON).body(userCreatedEvent);
        ResponseEntity<String> resourceEntity = restTemplate.exchange(request, new ParameterizedTypeReference<String>() {});
        log.debug("REST call returned: {}", resourceEntity.getBody());
    }

}
