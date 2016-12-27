package microservices4vaadin.authserver.service;

import java.io.IOException;
import java.util.Map;

import freemarker.template.TemplateException;

public interface FreemarkerEmailService {

    void sendMail(String templateName, Map<String, String> emailModel,
            Map<String, Object> model) throws IOException, TemplateException;

}
