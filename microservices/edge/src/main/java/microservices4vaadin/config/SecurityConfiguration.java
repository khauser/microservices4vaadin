package microservices4vaadin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableOAuth2Sso
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.portMapper().http(8080).mapsTo(8443);

        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);

        http.logout().logoutSuccessHandler(logoutSuccessHandler()).permitAll()
            .and()
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/webjars/**", "/", "/index.html", "/empty.html", "/login.html").permitAll()
                .antMatchers("/authserver/uaa/login", "/authserver/uaa/register", "/authserver/uaa/activate").permitAll()
                .antMatchers("/ui/VAADIN/**").permitAll()
                .anyRequest().authenticated()
            .and().csrf().disable().requiresChannel().anyRequest().requiresSecure();
    }

}