package microservices4vaadin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import microservices4vaadin.auth.Authority;

/**
 * A Spring data JPA repository for looking up granted authorities from the database
 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    Authority findOneByName(@Param("name") String name);

}