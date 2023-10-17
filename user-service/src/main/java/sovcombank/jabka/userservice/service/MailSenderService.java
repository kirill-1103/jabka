package sovcombank.jabka.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sovcombank.jabka.userservice.model.ActivationToken;

@Service
@PropertySource(value = "classpath:application.yml")
public class MailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailFrom;
    @Value("${mail-service.activation.uri}")
    private String activationUri;

    public void sendVerificationEmail(String userEmail, ActivationToken activationToken){
        String subject = "Email address confirmation";
        String messageBody = String.format("Для подтверждения Ваше электронной почты и " +
                "активации аккаунта необходимо перейти по ссылке %s", activationUri);
        messageBody = messageBody.replace("{id}", activationToken.getToken());

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(userEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);

        mailSender.send(mailMessage);
    }
}
