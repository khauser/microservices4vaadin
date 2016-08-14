package microservices4vaadin.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Repository;

import microservices4vaadin.auth.AcmeUser;

/**
 * AcmeUser Repository
 */
@Repository
public interface UserRepository extends JpaRepository<AcmeUser, String> {

    @Query("select u from AcmeUser u where u.activationKey = ?1")
    AcmeUser getUserByActivationKey(String activationKey);

    @Query("select u from AcmeUser u where u.activated = false and u.createdDateTime > ?1")
    List<AcmeUser> findNotActivatedUsersByCreatedDateTimeBefore(@DateTimeFormat(iso=ISO.DATE) Date dateTime);

    AcmeUser findOneByEmail(String email);

}
