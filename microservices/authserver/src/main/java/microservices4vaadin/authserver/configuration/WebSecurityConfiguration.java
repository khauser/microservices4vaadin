package microservices4vaadin.authserver.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import microservices4vaadin.authserver.service.AcmeUserDetailsService;

/**
 * Defines the Spring Security authentication, required for authenticating users
 */
@Configuration
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AcmeUserDetailsService userDetailsService;

    @Autowired
    void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(userDetailsService.getPasswordEncoder());
        auth.parentAuthenticationManager(this.authenticationManager());
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().loginPage("/login").successHandler(successHandler()).permitAll()
        .and()
                .requestMatchers().antMatchers("/user", "/login", "/logout", "/oauth/authorize", "/oauth_confirm_access", "/register", "/activate/**")
        .and()
                .authorizeRequests().antMatchers("/register", "/activate/**").permitAll()
        .and()
                .authorizeRequests().anyRequest().authenticated().and().csrf().disable();
    }

}
