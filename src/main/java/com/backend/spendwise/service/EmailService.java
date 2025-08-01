package com.backend.spendwise.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


// This service handles email sending functionality, such as sending activation emails to users.
@Service
@RequiredArgsConstructor
public class EmailService 
{
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmail(String to, String subject, String body) {
        try
        {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);            
            message.setText(body);

            mailSender.send(message);
            System.out.println("✅ Email sent successfully from: " + fromEmail + " to: " + to);
        }
        catch (Exception e)
        {
            System.err.println("❌ Error sending email from: " + fromEmail + " to: " + to);            
            System.out.println("Error sending email: " + e.getMessage());
        }
    }
}