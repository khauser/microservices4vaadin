package microservices4vaadin.auth;

//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import javax.validation.constraints.NotNull;
//
//import lombok.Data;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
///**
// * Entity representing a user
// */
//@Data
//public class AcmeUser implements Serializable {
//
//    private static final long serialVersionUID = 5862624106931867852L;
//
//    public AcmeUser() {}
//
//    public AcmeUser(AcmeUser acmeUser) {
//        super();
//        this.itemId = acmeUser.itemId;
//        this.email = acmeUser.email;
//        this.password = acmeUser.password;
//        this.firstName = acmeUser.firstName;
//        this.lastName = acmeUser.lastName;
//        this.activated = acmeUser.activated;
//        this.activationKey = acmeUser.activationKey;
//        this.authorities = acmeUser.authorities;
//        this.createdBy = acmeUser.createdBy;
//        this.createdDate = acmeUser.createdDate;
//        this.lastModifiedBy = acmeUser.lastModifiedBy;
//        this.lastModifiedDate = acmeUser.lastModifiedDate;
//    }
//
//    private long itemId;
//
//    @NotNull
//    private String email;
//
//    private String password;
//
//    private String firstName;
//
//    private String lastName;
//
//    boolean activated = false;
//
//    private String activationKey;
//
//    @JsonIgnore
//    private List<Authority> authorities = new ArrayList<Authority>();
//
//    @JsonIgnore
//    @NotNull
//    private String createdBy;
//
//    @JsonIgnore
//    @NotNull
//    private Date createdDate = new Date();
//
//    @JsonIgnore
//    private String lastModifiedBy;
//
//    @JsonIgnore
//    private Date lastModifiedDate = new Date();
//
//}
