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

    private final String apiKeyTest = "552470a3305e49f7b79011cd3699486e";

    @Test
    public void registerClientShouldReturnErrorMessageWhenEmailIsInvalid() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>("{\"name\":\"Test Jackson\", \"email\":\"invalidemail\"}", headers);

        ResponseEntity<Exception> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/clients/register", request, Exception.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getMessage()).contains("Email address is invalid");
    }

    @Test
    public void registerClientShouldReturnErrorMessageWhenEmailAlreadyExists() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>("{\"name\":\"Test Jackson\", \"email\":\"test.tom@email.com\"}", headers);

        ResponseEntity<Exception> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/clients/register", request, Exception.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getMessage()).contains("Email address is already in use");
    }

    @Test
    public void registerClientShouldReturnErrorMessageWhenNameIsTooLong() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>("{\"name\":\"Test Jackson Toooooooooooooooooooooooooo Loooooooooooooooooooooooooooong Naaaaaaaaaaaaaaaaaaaaaaaaaaame\", \"email\":\"test.jackson@email.com\"}", headers);

        ResponseEntity<Exception> responseEntity = restTemplate.postForEntity("http://localhost:" + port + "/clients/register", request, Exception.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getMessage()).contains("Name is longer than");
    }

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

    @Test
    public void getPositionShouldReturnErrorMessageWhenIdIsInvalid() {
        ResponseEntity<Exception> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/positions/invalid", Exception.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getMessage()).startsWith("Invalid position ID");
    }

    @Test
    public void getPositionShouldReturnCorrectPosition() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/positions/1",
                String.class))
                .contains("bartender").contains("Budapest");
    }

    @Test
    public void addPositionShouldReturnErrorMessageWhenNameOrLocationAreTooLong() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request =
                new HttpEntity<>("{\"name\":\"looooooooooooooooooooongpositiooooooooooooooooooooooooooon\", \"location\":\"looooooooooooooooooooonglocatioooooooooooooooooooooooooooooooooon\"}", headers);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/positions/create?apiKey=" + apiKeyTest,
                request, String.class))
                .contains("Name is longer than").contains("Location is longer than");
    }

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

    @Test
    public void searchPositionShouldReturnErrorMessageWhenKeywordOrLocationAreTooLong() {
        String keyword = "keywordistoooooooooooooooooooolooooooooooooooooooooong";
        String location = "locationistoooooooooooooooooooolooooooooooooooooooooong";

        ResponseEntity<Exception> responseEntity = restTemplate.getForEntity("http://localhost:" + port + "/positions/search?keyword=" + keyword + "&location=" + location + "&apiKey=" + apiKeyTest, Exception.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody().getMessage()).contains("Keyword is longer than");
        assertThat(responseEntity.getBody().getMessage()).contains("Location is longer than");
    }
}