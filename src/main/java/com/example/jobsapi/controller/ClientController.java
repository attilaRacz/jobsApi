package com.example.jobsapi.controller;

import com.example.jobsapi.model.Client;
import com.example.jobsapi.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    ClientService clientService;

    @PostMapping("/registerClient")
    public UUID registerClient(@RequestBody Client newClient) throws Exception {
        return clientService.addClient(newClient);
    }
}
