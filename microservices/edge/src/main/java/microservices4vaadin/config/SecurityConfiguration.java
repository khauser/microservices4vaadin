package microservices4vaadin.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.Http401AuthenticationEntryPoint;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableOAuth2Sso
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout().and()
                .exceptionHandling()
                .authenticationEntryPoint(
                        new Http401AuthenticationEntryPoint(
                                "Session realm=\"SESSION\""))
                .and()
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/webjars/**", "/", "/index.html", "/home.html", "/login.html").permitAll()
                .antMatchers("/authserver/uaa/login").permitAll()
                .antMatchers("/ui/VAADIN/**").permitAll()
                .antMatchers("/ui/vaadinServlet/UIDL/**").permitAll()
                .antMatchers("/ui/vaadinServlet/HEARTBEAT/**").permitAll()
                .anyRequest().authenticated().and().csrf().disable();
//                .csrfTokenRepository(csrfTokenRepository()).and()
//                .addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);
    }

//    private Filter csrfHeaderFilter() {
//        return new OncePerRequestFilter() {
//            @Override
//            protected void doFilterInternal(HttpServletRequest request,
//                    HttpServletResponse response, FilterChain filterChain)
//                            throws ServletException, IOException {
//                CsrfToken csrf = (CsrfToken) request
//                        .getAttribute(CsrfToken.class.getName());
//                if (csrf != null) {
//                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
//                    String token = csrf.getToken();
//                    if (cookie == null
//                            || token != null && !token.equals(cookie.getValue())) {
//                        cookie = new Cookie("XSRF-TOKEN", token);
//                        cookie.setPath("/");
//                        response.addCookie(cookie);
//                    }
//                }
//                filterChain.doFilter(request, response);
//            }
//        };
//    }
//
//    private CsrfTokenRepository csrfTokenRepository() {
//        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//        repository.setHeaderName("X-XSRF-TOKEN");
//        return repository;
//    }

}