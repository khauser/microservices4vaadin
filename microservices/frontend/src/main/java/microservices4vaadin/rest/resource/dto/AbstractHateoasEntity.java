package microservices4vaadin.rest.resource.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstractHateoasEntity implements Serializable {

    private static final long serialVersionUID = 1307388647540531446L;

    @Getter
    @Setter
    @JsonIgnore
    private List<Link> relationalLinks;

}
