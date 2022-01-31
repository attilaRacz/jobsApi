package com.example.jobsapi.service;

import com.example.jobsapi.dao.PositionRepository;
import com.example.jobsapi.model.Position;
import com.example.jobsapi.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class PositionService {

    @Autowired
    private Validator validator;

    @Autowired
    private PositionRepository positionRepository;

    public String addPosition(Position newPosition) throws Exception {
        Set<String> violations = validator.validatePosition(newPosition);

        if (!violations.isEmpty()) {
            // todo - find or implement right exception
            throw new Exception("Validation error: " + violations);
        }

        String positionUrl = positionRepository.save(newPosition).getUrl();

        // todo - should be a logger
        System.out.println("New position persisted: " + newPosition);

        return positionUrl;
    }

    public void validateApiKey(String apiKeyString) throws Exception {
        UUID apiKey = UUID.fromString(apiKeyString.replaceAll("(.{8})(.{4})(.{4})(.{4})(.+)", "$1-$2-$3-$4-$5"));

        if (!validator.apiKeyIsValid(apiKey)) {
            throw new Exception("Invalid api key");
        }
    }
}
