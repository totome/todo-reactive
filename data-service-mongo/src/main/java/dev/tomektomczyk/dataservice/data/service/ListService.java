package dev.tomektomczyk.dataservice.data.service;

import dev.tomektomczyk.dataservice.data.entity.ToDoList;
import dev.tomektomczyk.dataservice.data.repository.ListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListService {

    private final ListRepository listRepository;

    public Flux<ToDoList> getAllLists() {
        return listRepository.findAll();
    }

    public Mono<ToDoList> save(ToDoList toDoLists) {
        return listRepository.save(toDoLists);
    }
}
