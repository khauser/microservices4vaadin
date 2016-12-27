package microservices4vaadin.authserver.service;

import org.springframework.mail.javamail.JavaMailSender;

public interface EmailService {

    JavaMailSender getMailSender();

}
