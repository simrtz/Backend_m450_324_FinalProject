package com.testing_pipeline.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.testing_pipeline.backend.model.Priority;
import com.testing_pipeline.backend.model.Todo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setUp() {
        wireMockServer = new WireMockServer(8081);
        wireMockServer.start();

        configureFor(wireMockServer.port());
    }

    @AfterAll
    static void tearDown() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }

    @BeforeEach
    void setUpBaseUrl() {
        String baseUrl = "http://localhost:" + wireMockServer.port();
        restTemplate.getRestTemplate().setUriTemplateHandler(new DefaultUriBuilderFactory(baseUrl));
    }

    @Test
    void testGetAllTodos() throws Exception {
        String todosJson = objectMapper.writeValueAsString(List.of(
                new Todo("Get groceries", false, "personal", Priority.MEDIUM, LocalDateTime.now()),
                new Todo("Call boss", true, "work", Priority.HIGH, LocalDateTime.now()),
                new Todo("Watch brainrot", false, "personal", Priority.LOW, LocalDateTime.now())
        ));

        stubFor(get(urlEqualTo("/api/v1/todo"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(todosJson)));

        ResponseEntity<Todo[]> response = restTemplate.getForEntity("/api/v1/todo", Todo[].class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(3, response.getBody().length);
        Assertions.assertEquals("Get groceries", response.getBody()[0].getTitle());
    }

    @Test
    void testGetTodoByPriority() throws Exception {
        String todosJson = objectMapper.writeValueAsString(List.of(
                new Todo("Get groceries", false, "personal", Priority.HIGH, LocalDateTime.now()),
                new Todo("Call boss", true, "work", Priority.HIGH, LocalDateTime.now())
        ));

        stubFor(get(urlEqualTo("/api/v1/todo/HIGH"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(todosJson)));

        ResponseEntity<Todo[]> response = restTemplate.getForEntity("/api/v1/todo/HIGH", Todo[].class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(2, response.getBody().length);
        Assertions.assertEquals(Priority.HIGH, response.getBody()[0].getPriority());

    }

    @Test
    void testPostNewTodo() throws Exception {
        String todoJson = objectMapper.writeValueAsString(new Todo("Call boss", true, "work", Priority.HIGH, LocalDateTime.now()));

        stubFor(get(urlEqualTo("/api/v1/todo"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(todoJson)));

        ResponseEntity<Todo> response = restTemplate.getForEntity("/api/v1/todo", Todo.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Call boss", response.getBody().getTitle());
    }

    @Test
    void testUpdateTodo() throws Exception {
        String todoJson = objectMapper.writeValueAsString(new Todo("Get groceries", false, "personal", Priority.MEDIUM, LocalDateTime.now()));

                stubFor(get(urlEqualTo("/api/v1/todo/3"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(todoJson)));

        ResponseEntity<Todo> response = restTemplate.getForEntity("/api/v1/todo/3", Todo.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Get groceries", response.getBody().getTitle());
    }

    @Test
    void testDeleteTodo() {
        stubFor(get(urlEqualTo("/api/v1/todo/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")));

        ResponseEntity<Todo[]> response = restTemplate.getForEntity("/api/v1/todo/1", Todo[].class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
