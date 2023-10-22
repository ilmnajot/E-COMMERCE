package uz.ilmnajot.registration.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendLinkToMail(String username, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("no-reply@gmail.com");
            message.setTo(username);
            message.setSubject("verify account");
            message.setText("<a href='http://localhost:5555/api/auth/validate' " + token + username +">!</a>");
            javaMailSender.send(message);
        } catch (Exception ignored) {
        }
    }
    public void sendMail(String username, String emailCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("no-reply@gmail.com");
            message.setTo(username);
            message.setSubject("verify account");
            message.setText(emailCode);
            javaMailSender.send(message);
        } catch (Exception ignored) {
        }
    }
}
