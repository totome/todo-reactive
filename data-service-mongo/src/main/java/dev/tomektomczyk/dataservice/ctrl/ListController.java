package dev.tomektomczyk.dataservice.ctrl;

import dev.tomektomczyk.dataservice.ctrl.dto.ToDoList;
import dev.tomektomczyk.dataservice.ctrl.dto.ToDoListBasicInfo;
import dev.tomektomczyk.dataservice.data.service.ToDoListService;
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

    private final ToDoListService toDoListService;

    @GetMapping
    public Flux<ToDoList> getAllLists() {
        return toDoListService.getAllLists()
                .doOnNext(list -> log.info("Received list: {}", list));
    }

    @PostMapping
    public Mono<ToDoListBasicInfo> saveList(@RequestBody Mono<ToDoList> toDoList) {
        return toDoListService.save(toDoList)
                .doOnNext(list -> log.info("Saved list: {}", list));
    }
}
