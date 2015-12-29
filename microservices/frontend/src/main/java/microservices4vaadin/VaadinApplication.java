package microservices4vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import com.vaadin.spring.annotation.EnableVaadin;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableCircuitBreaker
@EnableDiscoveryClient
@ComponentScan
@Slf4j
@EnableVaadin
public class VaadinApplication {

    public static void main(String[] args) {
        log.info("Starting LessorApplication");
        SpringApplication.run(VaadinApplication.class, args);
    }

}

