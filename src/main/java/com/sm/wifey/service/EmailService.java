package com.sm.wifey.service;

import com.sm.wifey.model.Contact;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

//    @Autowired
    JavaMailSender mailSender;

    public void sendContactEmail(Contact contactForm) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("swathi7anantha@gmail.com");
        message.setSubject("New Contact Form: " + contactForm.getSubject());
        message.setText("Name: " + contactForm.getName() + "\n"
                + "Email: " + contactForm.getEmail() + "\n\n"
                + "Message:\n" + contactForm.getMessage());
        mailSender.send(message);
    }

}
