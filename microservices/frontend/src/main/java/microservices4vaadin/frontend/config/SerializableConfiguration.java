package microservices4vaadin.frontend.config;

import org.jdal.aop.SerializableProxyAdvisor;
import org.jdal.aop.config.SerializableAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class SerializableConfiguration {

    @Bean
    public SerializableAnnotationBeanPostProcessor serializableAnnotationBeanPostProcessor() {
        return new SerializableAnnotationBeanPostProcessor();
    }

    @Bean
    @Scope("prototype")
    public SerializableProxyAdvisor serializableProxyAdvisor() {
        return new SerializableProxyAdvisor();
    }

}
