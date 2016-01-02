package microservices4vaadin.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import microservices4vaadin.auth.AcmeUserDetails;

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
        else if(principal instanceof UsernamePasswordAuthenticationToken) {
            return (AcmeUserDetails)userDetailsService.loadUserByUsername(principal.getName());
        }
        return null;
    }

}
