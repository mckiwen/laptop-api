package com.example.API.REST.Ejercicio.controllers;

import com.example.API.REST.Ejercicio.entities.Portatil;
import com.example.API.REST.Ejercicio.repositories.PortatilRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PortatilControllerTest {

    @Autowired
    private static PortatilRepository portatilRepository;

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @BeforeAll
    static void beforeAll(@Autowired PortatilRepository portatilRepository) {
        Portatil portatil1 = new Portatil();
        portatil1.setId(null);
        portatil1.setPulgadas(15.6F);
        portatil1.setMarca("Lenovo");
        portatil1.setSo("Windows");
        portatil1.setReleaseDate(LocalDate.of(2020, 10, 7));
        Portatil portatil2 = new Portatil();
        portatil2.setId(null);
        portatil2.setPulgadas(17.0F);
        portatil2.setMarca("Asus");
        portatil2.setSo("Linux");
        portatil2.setReleaseDate(LocalDate.of(2023, 5, 1));

        portatilRepository.save(portatil1);
        portatilRepository.save(portatil2);

    }

    @DisplayName("GET /api/portatiles")
    @Test
    @Order(1)
    void findAll(){
        ResponseEntity<List> response = testRestTemplate.getForEntity("/api/portatiles", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @DisplayName("GET /api/portatiles/{id}")
    @Test
    @Order(2)
    void findOneById(){
        ResponseEntity<Portatil> response1 = testRestTemplate.getForEntity("/api/portatiles/2", Portatil.class);
        ResponseEntity<Portatil> response2 = testRestTemplate.getForEntity("/api/portatiles/999", Portatil.class);

        Portatil portatil2 = new Portatil();
        portatil2.setId(2L);
        portatil2.setPulgadas(17.0F);
        portatil2.setMarca("Asus");
        portatil2.setSo("Linux");
        portatil2.setReleaseDate(LocalDate.of(2023, 5, 1));

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());
        assertEquals(response1.getBody(), portatil2);
        assertNull(response2.getBody());
    }

    @DisplayName("POST /api/portatiles")
    @Test
    @Order(3)
    void create(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // enviamos JSON
        headers.setAccept(List.of(MediaType.APPLICATION_JSON)); // Recibimos una lista JSON

        Portatil portatil = new Portatil();
        portatil.setPulgadas(19.0F);
        portatil.setMarca("MSI");
        portatil.setSo("Windows");
        portatil.setReleaseDate(LocalDate.of(2022, 5, 1));

        HttpEntity<Portatil> request = new HttpEntity<>(portatil,headers);
        ResponseEntity<Portatil> response = testRestTemplate.exchange(
                "/api/portatiles",
                HttpMethod.POST,
                request,
                Portatil.class);

        Portatil result = response.getBody();
        assert result != null;
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3L, result.getId());
        assertEquals("Windows", result.getSo());


        Portatil portatil2 = new Portatil();
        portatil2.setId(6L);
        portatil2.setPulgadas(12.0F);
        portatil2.setMarca("Toshiba");
        portatil2.setSo("Windows");
        portatil2.setReleaseDate(LocalDate.of(2021, 5, 1));

        HttpEntity<Portatil> request2 = new HttpEntity<>(portatil2,headers);
        ResponseEntity<Portatil> response2 = testRestTemplate.exchange(
                "/api/portatiles",
                HttpMethod.POST,
                request2,
                Portatil.class);

        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
    }


    @DisplayName("PUT /api/portatiles")
    @Test
    @Order(4)
    void update(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // enviamos JSON
        headers.setAccept(List.of(MediaType.APPLICATION_JSON)); // Recibimos una lista JSON

        Portatil portatil1 = new Portatil();
        portatil1.setId(null);
        portatil1.setPulgadas(15.6F);
        portatil1.setMarca("Lenovo");
        portatil1.setSo("Windows 11");
        portatil1.setReleaseDate(LocalDate.of(2023, 10, 7));

        HttpEntity<Portatil> request = new HttpEntity<>(portatil1,headers);
        ResponseEntity<Portatil> response = testRestTemplate.exchange(
                "/api/portatiles",
                HttpMethod.PUT,
                request,
                Portatil.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        portatil1.setId(999L);
        HttpEntity<Portatil> request2 = new HttpEntity<>(portatil1,headers);
        ResponseEntity<Portatil> response2 = testRestTemplate.exchange(
                "/api/portatiles",
                HttpMethod.PUT,
                request2,
                Portatil.class);

        assertEquals(HttpStatus.NOT_FOUND, response2.getStatusCode());

        portatil1.setId(1L);
        HttpEntity<Portatil> request3 = new HttpEntity<>(portatil1,headers);
        ResponseEntity<Portatil> response3 = testRestTemplate.exchange(
                "/api/portatiles",
                HttpMethod.PUT,
                request3,
                Portatil.class);

        Portatil result = response3.getBody();
        assertEquals(HttpStatus.OK, response3.getStatusCode());
        assertEquals(1L, result.getId());
        assertEquals("Windows 11", result.getSo());
    }

    @DisplayName("DELETE /api/portatiles/{id}")
    @Test
    @Order(5)
    void delete(){
        ResponseEntity<Portatil> response = testRestTemplate.exchange(
                "/api/portatiles/999",
                HttpMethod.DELETE,
               null,
                Portatil.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ResponseEntity<Portatil> response1 = testRestTemplate.exchange(
                "/api/portatiles/1",
                HttpMethod.DELETE,
                null,
                Portatil.class);

        assertEquals(HttpStatus.NO_CONTENT, response1.getStatusCode());
    }

    @DisplayName("DELETE /api/portatiles")
    @Test
    @Order(6)
    void deleteAll(){
        ResponseEntity<Portatil> response = testRestTemplate.exchange(
                "/api/portatiles",
                HttpMethod.DELETE,
                null,
                Portatil.class);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
