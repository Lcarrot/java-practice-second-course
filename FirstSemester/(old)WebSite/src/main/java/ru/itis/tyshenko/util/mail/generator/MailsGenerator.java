package ru.itis.tyshenko.util.mail.generator;

public interface MailsGenerator {

    String generateConfirmEmail(String serverUrl, String code);
}
