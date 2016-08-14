package microservices4vaadin.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Thrown when the user attempting to authenticate is not activated
 */
public class WrongOldPasswordException extends AuthenticationException{

    private static final long serialVersionUID = 7048741709917583284L;

    public WrongOldPasswordException(String msg) {
        super(msg);
        // TODO Auto-generated constructor stub
    }

}
