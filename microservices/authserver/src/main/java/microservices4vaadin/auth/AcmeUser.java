package microservices4vaadin.auth;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


/**
 * Entity representing a user
 */
@Entity
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long itemId;

    @Email
    @NotNull
    @Size(min = 0, max = 100)
    @Column(length = 100, unique = true)
    private String email;

    @JsonIgnore
    @Size(min = 0, max = 100)
    @Column(length = 100)
    private String password;

    boolean activated = false;

    @Size(min = 0, max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_2_authority",
    joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities = new ArrayList<Authority>();

    @NotNull
    @Column(nullable = false, length = 50, updatable = false)
    private String createdBy;

    @NotNull
    @Column(nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentLocalDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDateTime;

}
