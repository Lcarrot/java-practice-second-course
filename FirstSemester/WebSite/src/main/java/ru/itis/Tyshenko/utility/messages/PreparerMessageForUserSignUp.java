package ru.itis.Tyshenko.utility.messages;

import ru.itis.Tyshenko.dto.UserDTO;
import ru.itis.Tyshenko.statuses.SignUpStatus;

import java.util.*;

import static ru.itis.Tyshenko.utility.DataChecker.*;


public class PreparerMessageForUserSignUp extends PreparerMessageUsingBuilder<SignUpStatus> {

    private final Map<SignUpStatus, String> statusDescription;
    private final List<SignUpStatus> statuses;
    private final UserDTO userDTO;

    public PreparerMessageForUserSignUp(UserDTO userDTO, String password, String confirmed_password) {
        this.userDTO = userDTO;
        statusDescription = fillMapDescriptions();
        statuses = getStatuses(password, confirmed_password);
    }

    public String getMessage(String... messages) {
        StringBuilder builder = concatAllErrorsSignUpStatus(statuses, statusDescription);
        for (String message : messages) {
            builder.append(message);
        }
        return builder.toString();
    }

    public boolean checkFields() {
        return statuses.get(0).equals(SignUpStatus.OK);
    }

    private  Map<SignUpStatus, String> fillMapDescriptions() {
        Map<SignUpStatus, String> statusDescription = new HashMap<>();
        statusDescription.put(SignUpStatus.WRONG_LOGIN, "login is incorrect, please use only latin letters and numbers," +
                " length of login must be more then 3 chars");
        statusDescription.put(SignUpStatus.WRONG_EMAIL, "Email is incorrect");
        statusDescription.put(SignUpStatus.WRONG_PASSWORD, "Password is incorrect, please use latin letters(need no less then 1 uppercase and 1 lowercase)," +
                "number(no less then 1, and special chars(@#%$^), DON'T USE SPACE");
        statusDescription.put(SignUpStatus.PASSWORDS_DO_NOT_MATCH, "password don't match");
        statusDescription.put(SignUpStatus.NO_CHOOSE_COUNTRY, "please, choose your country");
        statusDescription.put(SignUpStatus.NO_CHOOSE_GENDER, "please, choose your gender");
        return statusDescription;
    }

    private List<SignUpStatus> getStatuses(String password, String confirm_password) {
        List<SignUpStatus> statuses = new LinkedList<>();
        if (!checkLogin(userDTO.getLogin())) statuses.add(SignUpStatus.WRONG_LOGIN);
        if (!checkEmail(userDTO.getEmail())) statuses.add(SignUpStatus.WRONG_EMAIL);
        if (!checkCountry(userDTO.getCountry())) statuses.add(SignUpStatus.NO_CHOOSE_COUNTRY);
        if (!checkGender(userDTO.getGender())) statuses.add(SignUpStatus.NO_CHOOSE_GENDER);
        if (!checkPassword(password)) statuses.add(SignUpStatus.WRONG_PASSWORD);
        if (!checkPasswordEquals(password, confirm_password)) statuses.add(SignUpStatus.PASSWORDS_DO_NOT_MATCH);
        if (statuses.size() == 0) statuses.add(SignUpStatus.OK);
        return statuses;
    }
}
