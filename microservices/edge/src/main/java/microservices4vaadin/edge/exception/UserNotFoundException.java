package microservices4vaadin.edge.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when the provided username is not found
 */
public class UserNotFoundException extends AuthenticationException {

    private static final long serialVersionUID = 6587656019426094542L;

    public UserNotFoundException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }

}
