package microservices4vaadin.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


/**
 * Entity representing a user
 */
@Data
public class AcmeUser implements Serializable {

    private static final long serialVersionUID = 5862624106931867852L;

    public AcmeUser() {}

    public AcmeUser(AcmeUser acmeUser) {
        super();
        this.itemId = acmeUser.itemId;
        this.email = acmeUser.email;
        this.password = acmeUser.password;
        this.activated = acmeUser.activated;
        this.activationKey = acmeUser.activationKey;
        this.authorities = acmeUser.authorities;
    }

    private long itemId;

    @NotNull
    private String email;

    private String password;

    boolean activated = false;

    private String activationKey;

    @JsonIgnore
    private List<Authority> authorities = new ArrayList<Authority>();

}
