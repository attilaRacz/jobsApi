package com.example.jobsapi.util;
import com.example.jobsapi.model.Client;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class Validator {

    public boolean isValidEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    public Set<ConstraintViolation> validateClient(Client newClient) {
        Set<ConstraintViolation> violations = new HashSet<>();

        // todo - implement

        return violations;
    }
}
