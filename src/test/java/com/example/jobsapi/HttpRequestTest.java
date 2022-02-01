package com.example.jobsapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String apiKeyTest = "552470a3305e49f7b79011cd3699486e";

    @Test
    public void registerClientShouldReturnErrorMessageWhenEmailIsInvalid() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>("{\"name\":\"Test Jackson Toooooooooooooooooooooooooo Loooooooooooooooooooooooooooong Naaaaaaaaaaaaaaaaaaaaaaaaaaame\", \"email\":\"invalidemail\"}", headers);

        ResponseEntity<Exception> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/clients/register", request, Exception.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getMessage()).contains("Name is longer than");
        assertThat(responseEntity.getBody().getMessage()).contains("Email address is invalid");
    }

    //todo registerClientShouldReturnErrorMessageWhenEmailAlreadyExists
    //todo registerClientShouldReturnErrorMessageWhenNameIsTooLong
    @Test
    public void createUserShouldReturnUUID() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>("{\"name\":\"Test Carl\", \"email\":\"test.carl@email.com\"}", headers);

        UUID.fromString(this.restTemplate.postForObject("http://localhost:" + port + "/clients/register",
                request,
                String.class).replace("\"", ""));
    }

    //todo getPositionShouldReturnErrorMessageWhenApiKeyIsInvalid
    //todo getPositionShouldReturnErrorMessageWhenIdIsInvalid
    @Test
    public void getPositionShouldReturnCorrectPosition() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/positions/1",
                String.class))
                .contains("bartender").contains("Budapest");
    }

    //todo addPositionShouldReturnErrorMessageWhenApiKeyIsInvalid
    //todo addPositionShouldReturnErrorMessageWhenNameOrLocationAreTooLong
    //todo addPositionShouldReturnUrlOfNewPosition
    @Test
    public void addPositionShouldReturnUrlOfNewPosition() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>("{\"name\":\"Test Carl\", \"email\":\"test.carl@email.com\"}", headers);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/positions/create?apiKey=" + apiKeyTest,
                request, String.class))
                .startsWith("http://localhost:" + port + "/positions/");
    }

    //todo searchPositionShouldReturnErrorMessageWhenApiKeyIsInvalid
    //todo searchPositionShouldReturnErrorMessageWhenKeywordOrLocationAreTooLong
    //todo searchPositionShouldReturnCorrectValues
    @Test
    public void searchPositionShouldReturnCorrectValues() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/positions/search?keyword=programmer&location=Paris&apiKey=" + apiKeyTest,
                String.class))
                .contains("programmer").contains("Paris");
    }
}