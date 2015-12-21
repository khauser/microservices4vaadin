package microservices4vaadin.controller;

import java.security.Principal;

import microservices4vaadin.persistence.AcmeUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Return information about the currently logged in user
 */
@RestController
class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public AcmeUserDetails user(Principal principal) {
        if(principal instanceof AcmeUserDetails) {
            return (AcmeUserDetails)principal;
        }
        else if(principal instanceof OAuth2Authentication) {
            return (AcmeUserDetails)userDetailsService.loadUserByUsername(principal.getName());
        }
        return null;
    }

}
