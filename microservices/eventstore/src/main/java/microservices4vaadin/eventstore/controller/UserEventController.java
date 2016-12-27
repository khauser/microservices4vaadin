package microservices4vaadin.eventstore.controller;

import javax.servlet.http.HttpServletResponse;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import microservices4vaadin.eventstore.command.CreateUserCommand;
import microservices4vaadin.userevents.UserCreatedEvent;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserEventController {

    @Autowired
    public CommandGateway commandGateway;

    @RequestMapping(value = "/createdEvent", method = RequestMethod.POST)
    public ResponseEntity<String> publishCreatedEvent(@RequestBody(required = true) UserCreatedEvent userCreatedEvent,
                    HttpServletResponse response) {

        log.debug("Adding User [{}] '{}'", userCreatedEvent.getId(), userCreatedEvent.getEmail());
        CreateUserCommand command = new CreateUserCommand(userCreatedEvent.getId()
                , userCreatedEvent.getEmail(), userCreatedEvent.getFirstName(), userCreatedEvent.getLastName());
        commandGateway.sendAndWait(command);
        log.info("Added User [{}] '{}'", userCreatedEvent.getId(), userCreatedEvent.getEmail());
        response.setStatus(HttpServletResponse.SC_CREATED);// Set up the 201 CREATED response
        return new ResponseEntity<String>("true", HttpStatus.CREATED);
    }

}
