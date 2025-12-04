package com.jpmc.midascore.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
     public boolean signUpMail(String toEmail, String userName){
         try{
             MimeMessage message = mailSender.createMimeMessage();
             MimeMessageHelper helper = new MimeMessageHelper(message,true);
             helper.setTo(toEmail);
             helper.setSubject("Welcome to MIDAS APP!");
             helper.setText("""
                    <h3>Hello %s,</h3>
                    <p>Thank you for registering with <b>Midas App</b>!</p>
                    <p>Your account has been successfully created.</p>
                    <br>
                    <p>Happy transaction! ✨</p>
                    <p>– The Midas Team</p>
                    """.formatted(userName), true);
             mailSender.send(message);
             return true;
         }catch (MessagingException e){
             return false;
         }
     }
}
