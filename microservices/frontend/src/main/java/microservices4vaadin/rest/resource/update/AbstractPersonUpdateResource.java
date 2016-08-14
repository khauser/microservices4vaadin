package microservices4vaadin.rest.resource.update;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractPersonUpdateResource {

    @JsonInclude(Include.NON_NULL)
    private String gender;

    @JsonInclude(Include.NON_NULL)
    private String firstName;

    @JsonInclude(Include.NON_NULL)
    private String lastName;

    private String title;

    private String phone;

    @JsonInclude(Include.NON_NULL)
    private String email;

}
