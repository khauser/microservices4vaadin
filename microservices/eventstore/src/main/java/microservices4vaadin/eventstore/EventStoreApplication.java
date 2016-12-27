package microservices4vaadin.eventstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EventStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventStoreApplication.class, args);
    }

}
