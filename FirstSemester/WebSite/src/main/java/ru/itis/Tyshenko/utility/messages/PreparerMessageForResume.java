package ru.itis.Tyshenko.utility.messages;

import ru.itis.Tyshenko.dto.ResumeDTO;
import ru.itis.Tyshenko.statuses.AdStatus;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static ru.itis.Tyshenko.utility.DataChecker.checkNonNullField;

public class PreparerMessageForResume extends PreparerMessageUsingBuilder<AdStatus> {

    private final Map<AdStatus, String> statusDescription;
    private final List<AdStatus> statuses;
    private ResumeDTO resumeDTO;

    public PreparerMessageForResume(ResumeDTO resumeDTO) {
        this.resumeDTO = resumeDTO;
        statusDescription = fillMapDescriptions();
        statuses = getStatuses();
    }

    private List<AdStatus> getStatuses() {
        List<AdStatus> statuses = new LinkedList<>();
        Field[] fields = resumeDTO.getClass().getDeclaredFields();
        for (Field field: fields) {
            field.setAccessible(true);
            if (checkNonNullField(field.toString())) {
                statuses.add(AdStatus.EMPTY_FIELDS);
                break;
            }
        }
        if (statuses.isEmpty()) statuses.add(AdStatus.OK);
        return statuses;
    }

    private  Map<AdStatus, String> fillMapDescriptions() {
        Map<AdStatus, String> statusDescription = new HashMap<>();
        statusDescription.put(AdStatus.EMPTY_FIELDS, "Please, fill all fields");
        return statusDescription;
    }

    @Override
    public String getMessage(String... messages) {
        StringBuilder builder = concatAllErrorsSignUpStatus(statuses, statusDescription);
        for (String message: messages) {
            builder.append(message).append("\n");
        }
        return builder.toString();
    }

    public boolean checkFields() {
        return statuses.get(0).equals(AdStatus.OK);
    }
}

