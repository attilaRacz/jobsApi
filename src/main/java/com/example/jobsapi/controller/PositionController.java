package com.example.jobsapi.controller;

import com.example.jobsapi.model.Position;
import com.example.jobsapi.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/positions")
public class PositionController {

    @Autowired
    PositionService positionService;

    @PostMapping("/create")
    public String addPosition(@RequestBody Position newPosition, @RequestParam String apiKey) throws Exception {
        positionService.validateApiKey(apiKey);
        return positionService.addPosition(newPosition);
    }
}