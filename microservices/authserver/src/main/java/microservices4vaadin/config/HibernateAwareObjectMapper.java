package microservices4vaadin.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class HibernateAwareObjectMapper extends ObjectMapper {

    private static final long serialVersionUID = 1L;

    public HibernateAwareObjectMapper() {
        super();
        Hibernate4Module module = new Hibernate4Module();

        module.enable(Hibernate4Module.Feature.FORCE_LAZY_LOADING);
        module.disable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
        registerModule(module);
    }

}
