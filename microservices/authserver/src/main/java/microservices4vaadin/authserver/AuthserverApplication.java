package microservices4vaadin.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EntityScan(basePackages ={ "microservices4vaadin.auth" })
@EnableDiscoveryClient
@EnableResourceServer
@EnableAuthorizationServer
public class AuthserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthserverApplication.class, args);
    }

}
