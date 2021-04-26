package ru.itis.tyshenko.validation.validator;

import org.springframework.beans.BeanWrapperImpl;
import ru.itis.tyshenko.validation.UnrepeatableFields;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UnrepeatableFieldsValidator implements ConstraintValidator<UnrepeatableFields,Object> {

    private String[] fieldNames;

    @Override
    public void initialize(UnrepeatableFields constraintAnnotation) {
        fieldNames = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        Object[] fields = new Object[fieldNames.length];
        for (int i = 0; i < fields.length; i++) {
            fields[i] = new BeanWrapperImpl(object).getPropertyValue(fieldNames[i]);
        }
        for (int i = 0; i < fields.length - 1; i++) {
            for (int k = i + 1; k < fields.length; k++) {
                if (fields[i] != null && fields[i].equals(fields[k])) return false;
            }
        }
        return true;
    }
}
