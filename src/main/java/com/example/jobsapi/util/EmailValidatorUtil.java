package com.example.jobsapi.util;
import org.apache.commons.validator.routines.EmailValidator;

public class EmailValidatorUtil {

    public boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
