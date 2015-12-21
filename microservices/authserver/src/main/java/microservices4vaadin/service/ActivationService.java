package microservices4vaadin.service;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import microservices4vaadin.auth.AcmeUser;
import microservices4vaadin.repository.UserRepository;

/**
 * Handles registering and activating users.  Also cleans up users who have not activated
 * in a reasonable amount of time
 */
@Service
public class ActivationService {

    private final Logger log = LoggerFactory.getLogger(ActivationService.class);

    @Autowired
    private UserRepository userRepository;


    /**
     * Activates the user with the given registration key
     * @param key - the activation key
     * @return The activated user
     */
    public AcmeUser activateUser(String key) {
        return Optional.ofNullable(userRepository.getUserByActivationKey(key))
                .map(user -> {
                    user.setActivated(true);
                    user.setActivationKey(null);
                    userRepository.save(user);
                    log.debug("Activated user: {}", user);
                    return user;
                })
                .orElse(null);
    }

    /**
     * Non-activated users should be automatically deleted after 3 days.
     * <p/>
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNonActivatedUsers() {
        DateTime now = new DateTime();
        List<AcmeUser> users = userRepository.findNotActivatedUsersByCreationDateBefore(now.minusDays(3).toDate());
        for (AcmeUser user : users) {
            log.debug("Deleting not activated user {}", user.getEmail());
            userRepository.delete(user);
        }
    }
}
