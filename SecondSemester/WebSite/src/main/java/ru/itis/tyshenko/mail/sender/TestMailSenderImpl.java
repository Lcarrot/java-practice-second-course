package ru.itis.tyshenko.mail.sender;

import lombok.extern.java.Log;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

@Log
@Component
@Profile("test")
public class TestMailSenderImpl implements MailSender {

    @Override
    public void sendEmail(String to, String from, String subject, String text) {
        log.log(Level.INFO, "message was sent by: " + from + " to: " + to);
        System.out.println(subject + "\n" + text);
    }
}
