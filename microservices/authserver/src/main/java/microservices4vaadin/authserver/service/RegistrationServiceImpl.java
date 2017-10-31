package microservices4vaadin.authserver.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import microservices4vaadin.auth.AcmeUser;
import microservices4vaadin.auth.Authorities;
import microservices4vaadin.auth.Authority;
import microservices4vaadin.authserver.repository.AuthorityRepository;
import microservices4vaadin.authserver.repository.UserRepository;
import microservices4vaadin.authserver.util.RandomUtil;

/**
 * Register new users
 */
@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private AcmeUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    /* (non-Javadoc)
     * @see microservices4vaadin.service.RegistrationService#registerUser(microservices4vaadin.service.Registration)
     */
    @Override
    public AcmeUser registerUser(Registration registration) {
        AcmeUser newUser = new AcmeUser();
        //default new users to ROLE_USER
        Authority authority = authorityRepository.findOneByName(Authorities.USER);
        List<Authority> authorities = new ArrayList<Authority>();
        String encryptedPassword = userDetailsService.getPasswordEncoder().encode(registration.getPassword());
        newUser.setEmail(registration.getEmail());
        newUser.setPassword(encryptedPassword);
        // new user is not active
        newUser.setActivated(false);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        newUser.setCreatedBy("system");
        newUser.setCreatedDateTime(LocalDateTime.now());
        userRepository.save(newUser);
        log.debug("Created User: {}", newUser);
        return newUser;
    }

}
