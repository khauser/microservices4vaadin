package microservices4vaadin.persistence;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * User Details implementation for an AcmeUser
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class AcmeUserDetails extends AcmeUser implements UserDetails {

    private static final long serialVersionUID = -2841246955880250736L;

    public AcmeUserDetails(){

    }

    public AcmeUserDetails(AcmeUser acmeUser) {
        super(acmeUser);
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActivated();
    }

}
