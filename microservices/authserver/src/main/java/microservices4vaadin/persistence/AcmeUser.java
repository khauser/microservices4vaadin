package microservices4vaadin.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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

import lombok.Data;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;


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
        this.firstName = acmeUser.firstName;
        this.lastName = acmeUser.lastName;
        this.activated = acmeUser.activated;
        this.activationKey = acmeUser.activationKey;
        this.authorities = acmeUser.authorities;
        this.createdBy = acmeUser.createdBy;
        this.createdDate = acmeUser.createdDate;
        this.lastModifiedBy = acmeUser.lastModifiedBy;
        this.lastModifiedDate = acmeUser.lastModifiedDate;
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

    @Size(min = 0, max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(min = 0, max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

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
    @JsonIgnore
    private String createdBy;

    @NotNull
    @Column(nullable = false)
    @JsonIgnore
    private Date createdDate = new Date();

    @Column(length = 50)
    @JsonIgnore
    private String lastModifiedBy;

    @JsonIgnore
    private Date lastModifiedDate = new Date();

}
