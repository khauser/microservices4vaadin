package microservices4vaadin.service;

import org.springframework.mail.javamail.JavaMailSender;

public interface EmailService {

    JavaMailSender getMailSender();

}
