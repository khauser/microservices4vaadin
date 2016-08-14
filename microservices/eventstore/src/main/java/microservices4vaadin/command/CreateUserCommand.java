package microservices4vaadin.command;

import org.axonframework.commandhandling.annotation.TargetAggregateIdentifier;

import lombok.Getter;

@Getter
public class CreateUserCommand {

    @TargetAggregateIdentifier
    private final Long id;
    private final String name;

    public CreateUserCommand(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
