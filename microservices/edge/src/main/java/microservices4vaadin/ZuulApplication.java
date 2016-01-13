package microservices4vaadin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;

import com.netflix.zuul.ZuulFilter;

@SpringBootApplication
@EnableZuulProxy
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }

    @Bean
    public ZuulFilter proxyReverseFilter(){
        return new ProxyReverseFilter();
    }

    @Bean
    public ProxyAwarePreDecorationFilter proxyFilter(ZuulProperties zuulProperties) {
        return new ProxyAwarePreDecorationFilter(zuulProperties.isAddProxyHeaders());
    }

}
