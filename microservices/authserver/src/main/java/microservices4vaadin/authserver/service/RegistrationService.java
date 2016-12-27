package microservices4vaadin.authserver.service;

import microservices4vaadin.auth.AcmeUser;


public interface RegistrationService {

    /**
     * Registers a new user
     *
     * @param email - the email for the new user
     * @param password - the password for the new user
     * @param firstName - the new users first name
     * @param lastName - the new users last name
     * @return the activation code
     */
    AcmeUser registerUser(Registration registration);

}