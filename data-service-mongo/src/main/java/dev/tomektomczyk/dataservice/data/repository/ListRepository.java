package dev.tomektomczyk.dataservice.data.repository;

import dev.tomektomczyk.dataservice.data.entity.ToDoList;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ListRepository extends ReactiveMongoRepository<ToDoList, String> {
}
