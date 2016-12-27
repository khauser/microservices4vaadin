package microservices4vaadin.frontend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.vaadin.spring.annotation.EnableVaadin;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EntityScan(basePackages ={ "microservices4vaadin.auth" })
@EnableDiscoveryClient
@Slf4j
@EnableVaadin
public class VaadinApplication {

    public static void main(String[] args) {
        log.info("Starting VaadinApplication");
        SpringApplication.run(VaadinApplication.class, args);
    }

}

