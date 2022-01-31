package com.example.jobsapi.dao;

import com.example.jobsapi.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ClientRepository extends CrudRepository<Client, UUID> {

    Client findByEmail(String email);
}
