package microservices4vaadin.frontend.config;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionStrategy;

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

    @Bean
    public HttpSessionStrategy httpSessionStrategy() {
      CookieHttpSessionStrategy cookieHttpSessionStrategy = new CookieHttpSessionStrategy();
      DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
      cookieSerializer.setCookieName("JSESSIONID");
      cookieSerializer.setCookiePath("/");
      cookieSerializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
      cookieHttpSessionStrategy.setCookieSerializer(cookieSerializer);
      return cookieHttpSessionStrategy;
    }

    @Bean
    public SessionSerializer springSessionDefaultRedisSerializer() {
        return new SessionSerializer();
    }

}
