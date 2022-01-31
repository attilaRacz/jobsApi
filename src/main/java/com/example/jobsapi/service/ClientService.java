package com.example.jobsapi.service;

import com.example.jobsapi.dao.ClientRepository;
import com.example.jobsapi.model.Client;
import com.example.jobsapi.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private Validator validator;

    @Autowired
    private ClientRepository clientRepository;

    public UUID addClient(Client newClient) throws Exception {
        Set<String> violations = validator.validateClient(newClient);

        if (!violations.isEmpty()) {
            // todo - find or implement right exception
            throw new Exception("Validation error: " + violations);
        }

        UUID apiKey = clientRepository.save(newClient).getId();

        // todo - should be a logger
        System.out.println("New client persisted: " + newClient);

        // todo - return UUID
        return apiKey;
    }
}
