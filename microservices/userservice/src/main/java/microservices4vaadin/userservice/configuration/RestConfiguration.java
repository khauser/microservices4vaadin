package microservices4vaadin.userservice.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RestConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        HttpClient httpClient = HttpClients.createDefault();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jackson2HalModule());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(objectMapper);
        restTemplate.getMessageConverters().add(0, converter);

        return restTemplate;
    }

    @Bean
    public SpelAwareProxyProjectionFactory projectionFactory() {
      return new SpelAwareProxyProjectionFactory();
    }

}
