package microservices4vaadin.userservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import microservices4vaadin.userservice.persistence.User;

@Configuration
@EnableJpaRepositories(basePackages={"microservices4vaadin.userservice.repository"}, repositoryImplementationPostfix = "CustomImpl")
public class CustomRepositoryConfiguration extends RepositoryRestConfigurerAdapter  {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(User.class);
    }

}
