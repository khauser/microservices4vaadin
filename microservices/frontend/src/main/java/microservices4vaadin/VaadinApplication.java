package microservices4vaadin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import com.vaadin.spring.annotation.EnableVaadin;

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
@ComponentScan
@EnableVaadin
public class VaadinApplication {

    private static final Logger LOG = LoggerFactory.getLogger(VaadinApplication.class);

    public static void main(String[] args) {
        LOG.info("Starting LessorApplication");
        SpringApplication.run(VaadinApplication.class, args);
    }

}

