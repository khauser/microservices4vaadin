package microservices4vaadin.service;

import java.io.IOException;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import microservices4vaadin.util.ConstantsUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Repository
@Transactional
@Service("freemarkerEmailService")
public class FreemarkerEmailServiceImpl implements FreemarkerEmailService {

    @Autowired
    private EmailService  emailService;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Override
    public void sendMail(String templateName, final Map<String, String> emailModel, Map<String, Object> model) throws IOException, TemplateException {

        Template t = freeMarkerConfigurer.getConfiguration().getTemplate(templateName);
        model.put("ctx", ConstantsUtil.SITEURL);
        final String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                 MimeMessageHelper message = new MimeMessageHelper(mimeMessage,true,"GBK");
                 message.setTo(emailModel.get(ConstantsUtil.MAIL_RECEIVE));
                 message.setSubject(emailModel.get(ConstantsUtil.MAIL_SUBJECT));
                 message.setFrom(emailModel.get(ConstantsUtil.MAIL_SEND));
                 message.setText(text, true);
            }
         };
         emailService.getMailSender().send(preparator);
    }

}
