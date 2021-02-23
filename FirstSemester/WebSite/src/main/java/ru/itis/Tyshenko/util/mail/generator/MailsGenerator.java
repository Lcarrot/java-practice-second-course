package ru.itis.Tyshenko.util.mail.generator;

public interface MailsGenerator {

    String generateConfirmEmail(String serverUrl, String code);
}
