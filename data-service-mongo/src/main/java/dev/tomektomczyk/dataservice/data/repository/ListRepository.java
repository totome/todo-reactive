package dev.tomektomczyk.dataservice.data.repository;

import dev.tomektomczyk.dataservice.data.entity.ToDoListEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ListRepository extends ReactiveMongoRepository<ToDoListEntity, String> {
}
