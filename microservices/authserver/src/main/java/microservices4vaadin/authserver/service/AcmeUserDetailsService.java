package microservices4vaadin.authserver.service;

import lombok.extern.slf4j.Slf4j;
import microservices4vaadin.auth.AcmeUser;
import microservices4vaadin.auth.AcmeUserDetails;
import microservices4vaadin.authserver.exception.UserNotActivatedException;
import microservices4vaadin.authserver.exception.UserNotFoundException;
import microservices4vaadin.authserver.exception.WrongOldPasswordException;
import microservices4vaadin.authserver.repository.UserRepository;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Loads user specific AcmeUser data
 */
@Component("userDetailsService")
@Slf4j
public class AcmeUserDetailsService implements UserDetailsService {

    private static final String SESSION_USER_ATTRIBUTE_NAME="user";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String email) {
        log.debug("Authenticating {}", email);
        AcmeUser acmeUser = findUserByEmail(email.toLowerCase());
        AcmeUserDetails acmeUserDetails = new AcmeUserDetails(acmeUser);
        httpSession.setAttribute(SESSION_USER_ATTRIBUTE_NAME, acmeUserDetails);
        return acmeUserDetails;
    }

    private AcmeUser findUserByEmail(String lowercaseEmail) {
        AcmeUser acmeUser = userRepository.findOneByEmail(lowercaseEmail);
        if (acmeUser == null) {
            throw new UserNotFoundException("User " + lowercaseEmail + " was not found in the database");
        } else if (!acmeUser.isActivated()) {
            throw new UserNotActivatedException("User " + lowercaseEmail + " was not activated");
        }
        return acmeUser;
    }

    @Transactional
    public UserDetails updateUserCredentials(String oldEmail, String email, String newPassword, String oldPassword) {
        AcmeUser acmeUser = findUserByEmail(oldEmail.toLowerCase()); // this would fail if email is wrong
        if (newPassword != null && !newPassword.equals("")) {
            if (!passwordEncoder.matches(oldPassword, acmeUser.getPassword()))
                throw new WrongOldPasswordException("Wrong old password for user " + email);
            acmeUser.setPassword(passwordEncoder.encode(newPassword));
        }
        if (email != null && !email.equals(""))
            acmeUser.setEmail(email);
        userRepository.save(acmeUser);
        AcmeUserDetails acmeUserDetails = new AcmeUserDetails(acmeUser);
        httpSession.setAttribute(SESSION_USER_ATTRIBUTE_NAME, acmeUserDetails);
        return acmeUserDetails;
    }
}
