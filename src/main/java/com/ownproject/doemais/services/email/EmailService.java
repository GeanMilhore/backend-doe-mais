package com.ownproject.doemais.services.email;

import com.ownproject.doemais.domain.email.EmailModel;
import com.ownproject.doemais.domain.email.status.StatusEmail;
import com.ownproject.doemais.repositories.email.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.Sent);
        } catch (MailException e){
            emailModel.setStatusEmail(StatusEmail.Error);
        } finally {
            return emailRepository.save(emailModel);
        }
    }
}
