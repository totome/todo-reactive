package dev.tomektomczyk.dataservice.data.service;

import dev.tomektomczyk.dataservice.ctrl.dto.ToDoList;
import dev.tomektomczyk.dataservice.ctrl.exception.EmptyTaskListException;
import dev.tomektomczyk.dataservice.data.entity.TaskEntity;
import dev.tomektomczyk.dataservice.data.entity.ToDoListEntity;
import dev.tomektomczyk.dataservice.data.repository.ListRepository;
import dev.tomektomczyk.dataservice.data.repository.TaskRepository;
import dev.tomektomczyk.dataservice.mapper.TaskMapper;
import dev.tomektomczyk.dataservice.mapper.ToDoListMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ToDoListService {

    private final ToDoListMapper toDoListMapper;

    private final TaskMapper taskMapper;

    private final TaskRepository taskRepository;

    private final ListRepository listRepository;

    public Flux<ToDoList> getAllLists() {
        return listRepository.findAll()
                .flatMap(this::getTasksForList);
    }

    private Mono<ToDoList> getTasksForList(ToDoListEntity toDoList) {
        final List<String> taskIds = toDoList.getTaskIds();
        return taskRepository.findAllById(taskIds)
                .map(taskMapper::toTaskDto)
                .collectList()
                .doOnNext(tasks -> log.info("Tasks for list of Id {} retrieved: {}", toDoList.getId(), tasks))
                .map(tasks -> toDoListMapper
                        .toToDoListDto(toDoList)
                        .withTasks(tasks));
    }

    public Mono<ToDoList> save(Mono<ToDoList> toDoList) {
        return toDoList.handle(this::validateInput)
                .cast(ToDoList.class)
                .flatMap(this::saveTasks)
                .map(toDoListMapper::toToDoListEntity)
                .flatMap(listRepository::save)
                .map(toDoListMapper::toToDoListDtoWithIds);
    }

    private void validateInput(ToDoList list, SynchronousSink<Object> sink) {
        if (list.tasks() == null){
            sink.error(new EmptyTaskListException("Task list cannot be empty", 100));
        } else {
            sink.next(list);
        }
    }

    private Mono<ToDoList> saveTasks(ToDoList toDoList) {
        return Flux.fromIterable(toDoList.tasks())
                .map(taskMapper::toTaskEntity)
                .doOnNext(task -> log.info("Saving Task: {}", task))
                .flatMap(taskRepository::save)
                .doOnNext(task -> log.info("Saved Task: {}", task))
                .cast(TaskEntity.class)
                .map(taskMapper::toTaskDto)
                .collectList()
                .map(toDoList::withTasks);
    }


}
