package dev.tomektomczyk.dataservice.ctrl;

import dev.tomektomczyk.dataservice.data.entity.ToDoList;
import dev.tomektomczyk.dataservice.data.service.ListService;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("list")
@RequiredArgsConstructor
@Slf4j
public class ListController {

    private final ListService listService;

    @GetMapping
    public Flux<ToDoList> getAllLists() {
        return listService.getAllLists()
                .doOnNext(list -> log.info("Received list: {}", list));
    }

    @PostMapping
    public Mono<ToDoList> saveList(@RequestBody ToDoList toDoList) {
        log.info("Received list to save: {}", toDoList);
        return listService.save(toDoList);
    }
}
