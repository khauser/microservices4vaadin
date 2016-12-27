package microservices4vaadin.authserver.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when the user attempting to authenticate is not activated
 */
public class UserNotActivatedException extends AuthenticationException{

    private static final long serialVersionUID = 7485453242456652318L;

    public UserNotActivatedException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }

}
