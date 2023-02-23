package com.skj.skjapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

    @Autowired
    JavaMailSender mailSender;

    public void sendAdminMail(String text){
        MimeMessagePreparator messagePreparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                message.setFrom("southernkaratejiujitsuservices@gmail.com");
                message.setTo("southernkaratejiujitsuservices@gmail.com");
                message.setSubject("FIRST CREATION -- password");
                message.setText(text, true);
            }
        };

        mailSender.send(messagePreparator);
    }

}
