package dev.tomektomczyk.dataservice.data.service;

import dev.tomektomczyk.dataservice.ctrl.dto.Task;
import dev.tomektomczyk.dataservice.ctrl.dto.ToDoList;
import dev.tomektomczyk.dataservice.ctrl.dto.ToDoListBasicInfo;
import dev.tomektomczyk.dataservice.ctrl.exception.EmptyTaskListException;
import dev.tomektomczyk.dataservice.data.entity.TaskEntity;
import dev.tomektomczyk.dataservice.data.entity.ToDoListEntity;
import dev.tomektomczyk.dataservice.data.repository.ListRepository;
import dev.tomektomczyk.dataservice.data.repository.TaskRepository;
import dev.tomektomczyk.dataservice.mapper.TaskMapper;
import dev.tomektomczyk.dataservice.mapper.ToDoListMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ToDoListServiceTest {

    @Mock
    private ListRepository toDoListRepository;

    @Mock
    private TaskRepository taskRepository;

    private ToDoListService toDoListService;

    @BeforeEach
    public void setUp() {
        toDoListService = new ToDoListService(
                new ToDoListMapper(),
                new TaskMapper(),
                taskRepository,
                toDoListRepository);
    }

    @Test
    public void givenTaskListIsNull_whenTryingToSaveTheList_ThenExceptionIsEmitted() {
        final Mono<ToDoList> toDoList = Mono.just(ToDoList
                .builder()
                .name("My List")
                .build());

        final Mono<ToDoListBasicInfo> result = toDoListService.save(toDoList);

        StepVerifier.create(result)
                .expectError(EmptyTaskListException.class)
                .verify();
    }

    @Test
    public void givenCorrectData_whenSAvingTheList_ThenSavedItemIsReturned() {

        given(taskRepository.save(any()))
                .willReturn(
                        Mono.just(TaskEntity.builder()
                                .id("1")
                                .name("Task 1")
                                .build()),
                        Mono.just(TaskEntity.builder()
                                .id("2")
                                .name("Task 2")
                                .build()));

        given(toDoListRepository.save(any()))
                .willReturn(Mono.just(ToDoListEntity.builder()
                        .id("1")
                        .name("My List")
                        .taskIds(List.of("1", "2"))
                        .build()));

        final Mono<ToDoList> toDoList = Mono.just(ToDoList
                .builder()
                .tasks(List.of(
                        Task.builder()
                                .name("Task 1")
                                .build(),
                        Task.builder()
                                .name("Task 2")
                                .build()))
                .name("My List")
                .build());

        final Mono<ToDoListBasicInfo> result = toDoListService.save(toDoList);

        StepVerifier.create(result)
                .expectNextMatches(l-> l.id() == "1" &&
                                l.name().equals("My List") &&
                                l.tasks().size() == 2)
                .verifyComplete();

        then(toDoListRepository).should().save(any());
        then(taskRepository).should(times(2)).save(any());
    }

}