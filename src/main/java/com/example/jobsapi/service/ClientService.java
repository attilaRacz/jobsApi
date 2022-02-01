package com.example.jobsapi.service;

import com.example.jobsapi.dao.ClientRepository;
import com.example.jobsapi.model.Client;
import com.example.jobsapi.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private Validator validator;

    @Autowired
    private ClientRepository clientRepository;

    public UUID addClient(Client newClient) throws IllegalArgumentException {
        validator.validateClient(newClient);

        return clientRepository.save(newClient).getId();
    }
}
