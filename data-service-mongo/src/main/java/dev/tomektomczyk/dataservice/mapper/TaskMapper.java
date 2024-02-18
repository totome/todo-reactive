package dev.tomektomczyk.dataservice.mapper;

import dev.tomektomczyk.dataservice.ctrl.dto.Task;
import dev.tomektomczyk.dataservice.data.entity.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task toTaskDto(TaskEntity toDoListEntity) {
        return Task.builder()
                .id(toDoListEntity.getId())
                .name(toDoListEntity.getName())
                .isDone(toDoListEntity.isDone())
                .build();
    }
    public TaskEntity toTaskEntity(Task toDoList) {
        return TaskEntity.builder()
                .id(toDoList.id())
                .name(toDoList.name())
                .isDone(toDoList.isDone())
                .build();
    }
}
