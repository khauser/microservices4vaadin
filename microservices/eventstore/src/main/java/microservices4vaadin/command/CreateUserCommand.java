package microservices4vaadin.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import lombok.Getter;

@Getter
public class CreateUserCommand {

    @TargetAggregateIdentifier
    private final Long id;
    private final String name;
    private final String firstName;
    private final String lastName;

    public CreateUserCommand(Long id, String name, String firstName, String lastName) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
