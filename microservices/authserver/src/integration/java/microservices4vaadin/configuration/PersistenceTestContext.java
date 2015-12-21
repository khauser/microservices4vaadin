package microservices4vaadin.configuration;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:integration.properties")
@Profile("test")
public class PersistenceTestContext {

    @Bean
    @ConfigurationProperties(prefix="datasource.test")
    public DataSource testDataSource() {
        return DataSourceBuilder.create().build();
    }

}
