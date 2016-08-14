package microservices4vaadin.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


/**
 * Defines the granted authorities currently held by an AcmeUser
 */
@Entity
@Data
public class Authority implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = -3022031591212475416L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long itemId;

    @NotNull
    @Size(min = 0, max = 50)
    @Column(length = 50)
    private String name;

    @Override
    public String getAuthority() {
        return new SimpleGrantedAuthority(name).getAuthority();
    }

}
