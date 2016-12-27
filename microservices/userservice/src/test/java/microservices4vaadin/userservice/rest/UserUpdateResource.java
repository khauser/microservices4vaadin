package microservices4vaadin.userservice.rest;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateResource {

    private Long itemId;

    @JsonInclude(Include.NON_NULL)
    private String firstName;

    @JsonInclude(Include.NON_NULL)
    private String lastName;

    @JsonInclude(Include.NON_NULL)
    private String createdBy;

    @JsonInclude(Include.NON_NULL)
    private LocalDateTime createdDateTime;

    @JsonInclude(Include.NON_NULL)
    private String lastModifiedBy;

    @JsonInclude(Include.NON_NULL)
    private LocalDateTime lastModifiedDateTime;

    @JsonInclude(Include.NON_NULL)
    private String language;

}
