package ru.itis.tyshenko.mail.generator;

public interface MailsGenerator {

    String generateConfirmEmail(String serverUrl, String code);
}
