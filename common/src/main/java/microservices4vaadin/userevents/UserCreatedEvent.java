package microservices4vaadin.userevents;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreatedEvent extends AbstractEvent {

    private static final long serialVersionUID = 8632225327849072369L;

    private String email;

    private String firstName;

    private String lastName;

}
