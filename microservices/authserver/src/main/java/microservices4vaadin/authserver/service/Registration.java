package microservices4vaadin.authserver.service;

import lombok.Data;

@Data
public class Registration {

    private String password;
    private String firstName;
    private String lastName;
    private String email;

}
