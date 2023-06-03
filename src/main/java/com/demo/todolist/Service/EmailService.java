package com.demo.todolist.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    public void sendMail(String toEmail, String subjet, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("{tu email}");
        message.setTo(toEmail);
        message.setSubject(subjet);
        message.setText("http://localhost:5173/reset-password/" + body);

        javaMailSender.send(message);
    }

    public String createRandomToken(){
        byte[] randomBytes = new byte[50];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}
