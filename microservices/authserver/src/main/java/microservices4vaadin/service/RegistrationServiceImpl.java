package microservices4vaadin.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import microservices4vaadin.auth.AcmeUser;
import microservices4vaadin.auth.Authorities;
import microservices4vaadin.auth.Authority;
import microservices4vaadin.repository.AuthorityRepository;
import microservices4vaadin.repository.UserRepository;
import microservices4vaadin.util.RandomUtil;

/**
 * Register new users
 */
@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /* (non-Javadoc)
     * @see microservices4vaadin.service.RegistrationService#registerUser(microservices4vaadin.service.Registration)
     */
    @Override
    public AcmeUser registerUser(Registration registration) {
        AcmeUser newUser = new AcmeUser();
        //default new users to ROLE_USER
        Authority authority = authorityRepository.findOneByName(Authorities.USER);
        List<Authority> authorities = new ArrayList<Authority>();
        String encryptedPassword = passwordEncoder.encode(registration.getPassword());
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
