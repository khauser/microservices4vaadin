package microservices4vaadin.authserver.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import microservices4vaadin.auth.AcmeUserDetails;
import microservices4vaadin.authserver.service.AcmeUserDetailsService;

/**
 * Return information about the currently logged in user
 */
@RestController
class UserController {

    @Autowired
    private AcmeUserDetailsService acmeUserDetailsService;

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public AcmeUserDetails user(Principal principal) {
        if(principal instanceof AcmeUserDetails) {
            return (AcmeUserDetails)principal;
        }
        else if(principal instanceof UsernamePasswordAuthenticationToken) {
            return (AcmeUserDetails)acmeUserDetailsService.loadUserByUsername(principal.getName());
        }
        return null;
    }

    @RequestMapping(value = "/user", method = RequestMethod.PATCH)
    public AcmeUserDetails updateUserCredentials(Principal principal, @RequestBody CredentialUpdateResource credentials) {
        return (AcmeUserDetails)acmeUserDetailsService.updateUserCredentials(principal.getName(), credentials.getEmail()
                , credentials.getNewPassword(), credentials.getOldPassword());
    }

}
