package com.example.jobsapi.util;
import com.example.jobsapi.dao.ClientRepository;
import com.example.jobsapi.model.Client;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class Validator {

    @Autowired
    private ClientRepository clientRepository;

    private static final int maxNameLength = 100;

    private static final Map<ViolationType, String> violationConstraints;
    static {
        violationConstraints = new HashMap<>();
        violationConstraints.put(ViolationType.NAME_TOO_LONG, "Name is longer than " + maxNameLength + " characters");
        violationConstraints.put(ViolationType.INVALID_EMAIL, "Email address is invalid");
        violationConstraints.put(ViolationType.EXISTING_EMAIL, "Email address already exists in the system");
    }

    public Set<String> validateClient(Client client) {
        Set<String> violations = new HashSet<>();

        if (!nameLengthIsValid(client.getName())) {
            violations.add(violationConstraints.get(ViolationType.NAME_TOO_LONG));
        }
        if (!emailIsValid(client.getEmail())) {
            violations.add(violationConstraints.get(ViolationType.INVALID_EMAIL));
        }
        if (!emailIsUnique(client.getEmail())) {
            violations.add(violationConstraints.get(ViolationType.EXISTING_EMAIL));
        }

        return violations;
    }

    private boolean nameLengthIsValid(String name) {
        return name.length() <= maxNameLength;
    }

    public boolean emailIsValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private boolean emailIsUnique(String email) {
        return clientRepository.findByEmail(email) == null;
    }
}
