package ru.itis.tyshenko.mail.sender;

public interface MailSender {

    void sendEmail(String to, String from, String subject, String text);
}
