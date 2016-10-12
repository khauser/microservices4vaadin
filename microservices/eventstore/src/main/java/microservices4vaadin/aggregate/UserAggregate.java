package microservices4vaadin.aggregate;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.eventsourcing.annotation.AbstractAnnotatedAggregateRoot;
import org.axonframework.eventsourcing.annotation.AggregateIdentifier;
import org.axonframework.eventsourcing.annotation.EventSourcingHandler;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import microservices4vaadin.command.CreateUserCommand;
import microservices4vaadin.userevents.UserCreatedEvent;

@Slf4j
@Getter
public class UserAggregate extends AbstractAnnotatedAggregateRoot<Object> {

    private static final long serialVersionUID = 198894074441714656L;

    @AggregateIdentifier
    private Long id;
    private String email;
    private String firstName;
    private String lastName;

    public UserAggregate() {
    }

    @CommandHandler
    public UserAggregate(CreateUserCommand command) {
        log.debug("Command: 'CreateUserCommand' received.");
        log.debug("Queuing up a new UserCreatedEvent for user '{}'", command.getId());

        UserCreatedEvent user = new UserCreatedEvent();
        user.setId(command.getId());
        user.setEmail(command.getName());
        user.setFirstName(command.getFirstName());
        user.setLastName(command.getLastName());
        apply(user);
    }

//    @CommandHandler
//    public void markActivated(ActivateUserCommand command) {
//        log.debug("Command: 'ActivateUserCommand' received.");
//        if (!this.isActivated()) {
//            apply(new UserActivatedEvent(id, name));
//        } else {
//            throw new IllegalStateException("This UserAggregate (" + this.getId() + ") is already activated.");
//        }
//    }

//    @CommandHandler
//    public void markUnsaleable(MarkProductAsUnsaleableCommand command) {
//        LOG.debug("Command: 'MarkProductAsUnsaleableCommand' received.");
//        if (this.isSaleable()) {
//            apply(new ProductUnsaleableEvent(id));
//        } else {
//            throw new IllegalStateException("This ProductAggregate (" + this.getId() + ") is already off-sale.");
//        }
//    }

    @EventSourcingHandler
    public void on(UserCreatedEvent event) {
        this.id = event.getId();
        this.email = event.getEmail();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        log.debug("Applied: 'UserCreatedEvent' [{}] '{}'", event.getId(), event.getEmail());
    }

//    @EventSourcingHandler
//    public void on(UserActivatedEvent event) {
//        this.isActivated = true;
//        log.debug("Applied: 'UserActivatedEvent' [{}]", event.getId());
//    }

}
