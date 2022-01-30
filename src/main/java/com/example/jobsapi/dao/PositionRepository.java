package com.example.jobsapi.dao;

import com.example.jobsapi.model.Position;
import org.springframework.data.repository.CrudRepository;

public interface PositionRepository extends CrudRepository<Position, Long> {
}
