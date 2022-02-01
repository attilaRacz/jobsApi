package com.example.jobsapi.service;

import com.example.jobsapi.dao.PositionRepository;
import com.example.jobsapi.model.Position;
import com.example.jobsapi.util.PositionSearchResult;
import com.example.jobsapi.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.InvalidKeyException;
import java.util.Optional;
import java.util.UUID;

@Service
public class PositionService {

    @Autowired
    private Validator validator;

    @Autowired
    private PositionRepository positionRepository;

    public Optional<Position> getPosition(String positionIdString) {
        Long positionId = validator.validatePositionId(positionIdString);
        return positionRepository.findById(positionId);
    }

    public String addPosition(Position newPosition, String requestUrl) {
        validator.validatePosition(newPosition);

        Long positionId = positionRepository.save(newPosition).getId();

        // todo - should be a logger
        System.out.println("New position persisted: " + newPosition);

        return requestUrl + positionId;
    }

    public PositionSearchResult[] searchPosition(String keyword, String location) {
        String ExternalApiUrl = "http://localhost:8080/externalApi/search";

        validator.validateExternalPositionSearch(keyword, location);

        String urlTemplate = createUrlTemplate(ExternalApiUrl, keyword, location);
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(urlTemplate, PositionSearchResult[].class).getBody();
    }

    private String createUrlTemplate(String url, String keyword, String location) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("keyword", keyword)
                .queryParam("location", location)
                .encode()
                .toUriString();
    }

    public void validateApiKey(String apiKeyString) throws InvalidKeyException {
        UUID apiKey = formatApiKey(apiKeyString);

        if (!validator.apiKeyIsValid(apiKey)) {
            throw new InvalidKeyException("Invalid api key");
        }
    }

    private UUID formatApiKey(String apiKeyString) {
        if (apiKeyString.contains("-")) {
            return UUID.fromString(apiKeyString);
        } else {
            return UUID.fromString(apiKeyString.replaceAll("(.{8})(.{4})(.{4})(.{4})(.+)", "$1-$2-$3-$4-$5"));
        }
    }
}
