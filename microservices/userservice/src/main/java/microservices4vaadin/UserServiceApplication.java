package microservices4vaadin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.DefaultCurieProvider;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication extends SpringBootServletInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceApplication.class);

    public static String CURIE_NAMESPACE = "microservice4vaadin";

    public @Bean CurieProvider curieProvider() {
        return new DefaultCurieProvider(CURIE_NAMESPACE, new UriTemplate("http://localhost:8082/profile/{rel}"));
    }

    public static void main(String[] args) {
        LOG.info("Starting AuthserverApplication");
        new SpringApplicationBuilder(UserServiceApplication.class).run(args);
    }

    /**
     * Allows the application to be started when being deployed into a Servlet 3 container.
     *
     * @see org.springframework.boot.web.SpringBootServletInitializer#configure(org.springframework.boot.builder.SpringApplicationBuilder)
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(UserServiceApplication.class);
    }
}
