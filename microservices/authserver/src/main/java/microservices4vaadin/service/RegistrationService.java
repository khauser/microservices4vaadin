package microservices4vaadin.service;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import microservices4vaadin.persistence.AcmeUser;
import microservices4vaadin.persistence.Authorities;
import microservices4vaadin.persistence.Authority;
import microservices4vaadin.repository.AuthorityRepository;
import microservices4vaadin.repository.UserRepository;
import microservices4vaadin.util.RandomUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Register new users
 */
@Service
@Slf4j
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Registers a new user
     *
     * @param email - the email for the new user
     * @param password - the password for the new user
     * @param firstName - the new users first name
     * @param lastName - the new users last name
     * @return the activation code
     */
    public String registerUser(Registration registration) {
        AcmeUser newUser = new AcmeUser();
        //default new users to ROLE_USER
        Authority authority = authorityRepository.findOneByName(Authorities.USER);
        List<Authority> authorities = new ArrayList<Authority>();
        String encryptedPassword = passwordEncoder.encode(registration.getPassword());
        newUser.setEmail(registration.getEmail());
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(registration.getFirstName());
        newUser.setLastName(registration.getLastName());
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        newUser.setCreatedBy("system");
        userRepository.save(newUser);
        log.debug("Created User: {}", newUser);
        return newUser.getActivationKey();
    }

}
