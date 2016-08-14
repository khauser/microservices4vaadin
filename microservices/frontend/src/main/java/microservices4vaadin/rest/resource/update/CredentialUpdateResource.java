package microservices4vaadin.rest.resource.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialUpdateResource {

    @JsonInclude(Include.NON_NULL)
    private String email;

    @JsonInclude(Include.NON_NULL)
    private String newPassword;

    @JsonInclude(Include.NON_NULL)
    private String oldPassword;

}
