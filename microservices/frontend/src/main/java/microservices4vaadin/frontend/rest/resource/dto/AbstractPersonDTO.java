package microservices4vaadin.frontend.rest.resource.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
public abstract class AbstractPersonDTO extends AbstractHateoasEntity {

    private static final long serialVersionUID = 860039839367963253L;

    public static enum Gender {
        MALE,
        FEMALE
    }

    private Long itemId;

    private Gender gender;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String title;

    private String phone;

    @NotNull
    @Size(min = 5, max = 255)
    private String email;

}
