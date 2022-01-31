package com.example.jobsapi.util;
import com.example.jobsapi.dao.ClientRepository;
import com.example.jobsapi.model.Client;
import com.example.jobsapi.model.Position;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Validator {

    @Autowired
    private ClientRepository clientRepository;

    private static final int maxClientNameLength = 100;
    private static final int maxPositionNameLength = 50;
    private static final int maxPositionLocationLength = 50;

    private static final Map<ViolationType, String> violationConstraints;
    static {
        violationConstraints = new HashMap<>();
        violationConstraints.put(ViolationType.CLIENT_NAME_TOO_LONG, "Name is longer than " + maxClientNameLength + " characters");
        violationConstraints.put(ViolationType.INVALID_EMAIL, "Email address is invalid");
        violationConstraints.put(ViolationType.EXISTING_EMAIL, "Email address already exists in the system");
        violationConstraints.put(ViolationType.POSITION_NAME_TOO_LONG, "Name is longer than " + maxPositionNameLength + " characters");
        violationConstraints.put(ViolationType.POSITION_LOCATION_TOO_LONG, "Location is longer than " + maxPositionLocationLength + " characters");
    }

    public Set<String> validateClient(Client client) {
        Set<String> violations = new HashSet<>();

        if (!lengthIsValid(client.getName(), maxClientNameLength)) {
            violations.add(violationConstraints.get(ViolationType.CLIENT_NAME_TOO_LONG));
        }
        if (!emailIsValid(client.getEmail())) {
            violations.add(violationConstraints.get(ViolationType.INVALID_EMAIL));
        }
        if (!emailIsUnique(client.getEmail())) {
            violations.add(violationConstraints.get(ViolationType.EXISTING_EMAIL));
        }

        return violations;
    }

    private boolean lengthIsValid(String name, int maxLength) {
        return name.length() <= maxLength;
    }

    public boolean emailIsValid(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private boolean emailIsUnique(String email) {
        return clientRepository.findByEmail(email) == null;
    }

    public Set<String> validatePosition(Position position) {
        Set<String> violations = new HashSet<>();

        if (!lengthIsValid(position.getName(), maxPositionNameLength)) {
            violations.add(violationConstraints.get(ViolationType.POSITION_NAME_TOO_LONG));
        }
        if (!lengthIsValid(position.getName(), maxPositionLocationLength)) {
            violations.add(violationConstraints.get(ViolationType.POSITION_LOCATION_TOO_LONG));
        }

        return violations;
    }

    public boolean apiKeyIsValid(UUID apiKey) {
        return clientRepository.existsById(apiKey);
    }
}
