package microservices4vaadin.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class SessionConfiguration {

    private final static String SESSION_SERIALIZATION_ID = "microservices4vaadin";

    @Autowired
    private ApplicationContext appContext;

    @Bean
    public String overwriteSerializationId() {
        BeanFactory beanFactory = appContext.getAutowireCapableBeanFactory();
        ((DefaultListableBeanFactory) beanFactory).setSerializationId(SESSION_SERIALIZATION_ID);
        return "overwritten";
    }

}