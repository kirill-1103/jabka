package sovcombank.jabka.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sovcombank.jabka.userservice.model.ActivationToken;
import sovcombank.jabka.userservice.model.RecoveryToken;

@Service
@PropertySource(value = "classpath:application.yml")
public class MailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String emailFrom;
    @Value("${mail-service.activation.uri}")
    private String activationUri;
    @Value("${mail-service.recovery.uri}")
    private String recoveryUrl;

    public void sendVerificationEmail(String userEmail, ActivationToken activationToken){
        String subject = "Email address confirmation";
        String messageBody = String.format("Для подтверждения Ваше электронной почты и " +
                "активации аккаунта необходимо перейти по ссылке %s", activationUri);
        messageBody = messageBody.replace("{token}", activationToken.getToken());

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(userEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);

        mailSender.send(mailMessage);
    }

    public void sendRecoveryPassword(String userEmail, RecoveryToken recoveryToken){
        String subject = "Password Recovery";
        String messageBody = String.format("Для сброса пароля " +
                "Вам необходимо перейти по ссылке %s", recoveryUrl);
        messageBody = messageBody.replace("{token}", recoveryToken.getToken());

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(emailFrom);
        mailMessage.setTo(userEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(messageBody);

        mailSender.send(mailMessage);
    }
}
