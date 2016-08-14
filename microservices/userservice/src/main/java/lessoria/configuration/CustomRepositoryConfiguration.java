package lessoria.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

import lessoria.persistence.User;

@Configuration
@EnableJpaRepositories(basePackages={"lessoria.repository"}, repositoryImplementationPostfix = "CustomImpl")
public class CustomRepositoryConfiguration extends RepositoryRestConfigurerAdapter  {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(User.class);
    }

}
