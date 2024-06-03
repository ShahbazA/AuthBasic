package com.example.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetMapping() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user1","password2").getForEntity(
                "http://localhost:" + port + "v1/api/home/hello", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hello, World!", response.getBody());
    }

    @Test
    public void testPostMapping(){
        ResponseEntity<String> response = restTemplate.withBasicAuth("user1","password2").postForEntity(
                "http://localhost:" + port + "v1/api/home/post-method", "Payload", String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Payload", response.getBody());
    }

    @Test
    public void testUnAuthPostMapping(){
//        ResponseEntity<String> response = restTemplate.withBasicAuth("user1","password21").postForEntity(
//                "http://localhost:" + port + "v1/api/home/post-method", "Payload", String.class);

        ResourceAccessException resourceAccessException = assertThrows(ResourceAccessException.class, () -> restTemplate
                .postForObject("http://localhost:" + port + "v1/api/home/post-method","Payload", String.class));

        assertThat(resourceAccessException).hasStackTraceContaining("cannot retry due to server authentication");
    }

    @Test
    public void testUnAuthGetMapping() {
        ResponseEntity<String> response = restTemplate.withBasicAuth("user1","password23").getForEntity(
                "http://localhost:" + port + "v1/api/home/hello", String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

}
