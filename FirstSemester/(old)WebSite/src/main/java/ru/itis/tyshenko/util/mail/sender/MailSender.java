package ru.itis.tyshenko.util.mail.sender;

public interface MailSender {

    void sendEmail(String to, String from, String subject, String text);
}
