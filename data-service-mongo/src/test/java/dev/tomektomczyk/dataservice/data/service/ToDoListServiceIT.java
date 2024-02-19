package dev.tomektomczyk.dataservice.data.service;

import dev.tomektomczyk.dataservice.ctrl.dto.InputValidationFailedResponse;
import dev.tomektomczyk.dataservice.data.repository.ListRepository;
import dev.tomektomczyk.dataservice.data.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import org.testcontainers.containers.MongoDBContainer;


@SpringBootTest
@AutoConfigureWebTestClient
@Testcontainers
public class ToDoListServiceIT {

    public static final String MONGO_IMAGE_NAME = "mongo:latest";

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ListRepository toDoListRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Container
    @ServiceConnection
    private static MongoDBContainer mongoDBContainer = new MongoDBContainer(MONGO_IMAGE_NAME);

    @BeforeEach
    void setUp() {
        toDoListRepository.deleteAll().block();
        taskRepository.deleteAll().block();
    }

    @Test
    public void givenTaskListIsNull_whenTryingToSaveTheList_ThenExceptionIsEmitted() {
        Flux<InputValidationFailedResponse> response = webTestClient.post()
                .uri("/list")
                .header("Content-Type", "application/json")
                .bodyValue("""
                        {
                            "name": "My list",
                            "tasks": null
                        }
                        """)
                .exchange()
                .expectStatus()
                .is4xxClientError()
                .expectHeader()
                .contentType("application/json")
                .returnResult(InputValidationFailedResponse.class)
                .getResponseBody();

        StepVerifier.create(response)
                .expectNextMatches(r -> r.message().equals("Task list cannot be empty"))
                .verifyComplete();
    }

    @Test
    public void givenCorrectToDoList_whenSaveTheList_ThenListIsSaved() {
        webTestClient.post()
                .uri("/list")
                .header("Content-Type", "application/json")
                .bodyValue("""
                        {
                            "name": "My list",
                            "tasks": [{
                                "name": "Task 1 name",
                                "isDone": false
                            }]
                        }
                        """)
                .exchange()
                .expectStatus()
                .is2xxSuccessful();

        webTestClient.get()
                .uri("/list")
                .exchange()
                .expectStatus()
                .is2xxSuccessful()
                .expectBody()
                .jsonPath("$.length()")
                .isEqualTo(1)
                .jsonPath("$[0].name")
                .isEqualTo("My list")
                .jsonPath("$[0].tasks.length()")
                .isEqualTo(1)
                .jsonPath("$[0].tasks[0].name")
                .isEqualTo("Task 1 name");
    }
}
