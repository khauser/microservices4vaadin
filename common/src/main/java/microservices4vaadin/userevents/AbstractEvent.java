package microservices4vaadin.userevents;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractEvent implements Serializable {

    private static final long serialVersionUID = -5858458671150375756L;

    private Long id;

}
