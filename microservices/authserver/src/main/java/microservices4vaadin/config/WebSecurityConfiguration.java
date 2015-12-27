package microservices4vaadin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import microservices4vaadin.service.AcmeUserDetailsService;

/**
 * Defines the Spring Security authentication, required for authenticating users
 */
@Configuration
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AcmeUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
         auth
             .userDetailsService(userDetailsService)
             .passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().loginPage("/login").permitAll()
        .and()
                .requestMatchers().antMatchers("/login", "/logout", "/oauth/authorize", "/oauth_confirm_access", "/register", "/activate/**")
        .and()
                .authorizeRequests().antMatchers("/register", "/activate/**").permitAll()
        .and()
                .authorizeRequests().anyRequest().authenticated();
    }

}
