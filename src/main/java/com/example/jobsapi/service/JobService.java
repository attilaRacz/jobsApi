package com.example.jobsapi.service;

import com.example.jobsapi.dao.ClientRepository;
import com.example.jobsapi.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Autowired
    private Validator validator;

    @Autowired
    private ClientRepository clientRepository;
}
