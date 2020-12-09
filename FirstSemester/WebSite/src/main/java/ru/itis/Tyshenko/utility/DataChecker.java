package ru.itis.Tyshenko.utility;

import java.util.regex.Pattern;

public class DataChecker {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+" +
            "(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    private static final Pattern LOGIN_PATTERN = Pattern.compile("^[a-zA-Z0-9]{3,}$");

    public static boolean checkLogin(String login) {
        return LOGIN_PATTERN.matcher(login).matches();
    }

    public static boolean checkEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean checkPassword(String password) {
        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean checkCountry(String country) {
        return country.length() > 0;
    }

    public static boolean checkPasswordEquals(String password, String confirmedPassword) {
        return password.equals(confirmedPassword);
    }

    public static boolean checkGender(String genderString) {
        return genderString != null && (genderString.equals("male") || genderString.equals("female"));
    }

    public static boolean checkNonNullField(String string) {
        return string != null && string.trim().equals("");
    }
}
