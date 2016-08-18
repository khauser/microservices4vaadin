package microservices4vaadin.rest.resource.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import microservices4vaadin.auth.AcmeUser;
import microservices4vaadin.rest.resource.dto.AbstractPersonDTO.Gender;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserServiceUserDTO implements Serializable {

    private static final long serialVersionUID = -7121116048753090277L;

    private Long itemId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String title;

    private String phone;

    private Gender gender;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDateTime;

    @JsonInclude(Include.NON_NULL)
    private String language;

    private AcmeUser acmeUser;

}
