package ru.itis.Tyshenko.utility.messages;

import ru.itis.Tyshenko.statuses.ChangeUserStatus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static ru.itis.Tyshenko.utility.DataChecker.*;

public class PreparerMessageForUserChange extends PreparerMessageUsingBuilder<ChangeUserStatus> {

    private final Map<ChangeUserStatus, String> statusDescription;
    private final List<ChangeUserStatus> statuses;
    private final String email;

    public PreparerMessageForUserChange(String email) {
        this.email = email;
        statusDescription = fillMapDescriptions();
        statuses = getStatuses();
    }

    @Override
    public String getMessage(String... messages) {
        StringBuilder builder = concatAllErrorsSignUpStatus(statuses, statusDescription);
        for (String message: messages) {
            builder.append(message).append("\n");
        }
        return builder.toString();
    }

    @Override
    public boolean checkFields() {
        return statuses.get(0).equals(ChangeUserStatus.OK);
    }

    private List<ChangeUserStatus> getStatuses() {
        List<ChangeUserStatus> statuses = new LinkedList<>();
        if (!checkEmail(email)) statuses.add(ChangeUserStatus.WRONG_EMAIL);
        if (statuses.isEmpty()) statuses.add(ChangeUserStatus.OK);
        return statuses;
    }

    private Map<ChangeUserStatus, String> fillMapDescriptions() {
        Map<ChangeUserStatus, String> map = new HashMap<>();
        map.put(ChangeUserStatus.WRONG_EMAIL, "Email is incorrect");
        return map;
    }
}
