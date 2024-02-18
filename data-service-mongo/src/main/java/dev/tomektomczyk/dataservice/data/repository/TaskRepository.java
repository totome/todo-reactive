package dev.tomektomczyk.dataservice.data.repository;

import dev.tomektomczyk.dataservice.data.entity.TaskEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TaskRepository  extends ReactiveMongoRepository<TaskEntity, String> {
}
