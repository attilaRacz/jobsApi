package com.example.jobsapi.controller;

import com.example.jobsapi.model.Position;
import com.example.jobsapi.service.PositionService;
import com.example.jobsapi.util.PositionSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidKeyException;
import java.util.Optional;

@RestController
@RequestMapping("/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @GetMapping("/{positionId}")
    public Optional<Position> getPosition(@PathVariable String positionId) {
        try {
            return positionService.getPosition(positionId);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/create")
    public String addPosition(@RequestBody Position newPosition, @RequestParam String apiKey, HttpServletRequest httpServletRequest) {
        try {
            positionService.validateApiKey(apiKey);
            return positionService.addPosition(newPosition, httpServletRequest.getRequestURL().toString().replace("create", ""));
        } catch (InvalidKeyException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    @GetMapping("/search")
    public PositionSearchResult[] searchPosition(@RequestParam String apiKey, @RequestParam String keyword, @RequestParam String location) {
        try {
            positionService.validateApiKey(apiKey);
            return positionService.searchPosition(keyword, location);
        } catch (InvalidKeyException e) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }
}