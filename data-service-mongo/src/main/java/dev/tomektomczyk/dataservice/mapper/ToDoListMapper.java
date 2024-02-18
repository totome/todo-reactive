package dev.tomektomczyk.dataservice.mapper;

import dev.tomektomczyk.dataservice.ctrl.dto.Task;
import dev.tomektomczyk.dataservice.ctrl.dto.ToDoList;
import dev.tomektomczyk.dataservice.data.entity.ToDoListEntity;
import org.springframework.stereotype.Component;

@Component
public class ToDoListMapper {
    public ToDoList toToDoListDto(ToDoListEntity toDoListEntity) {
        return ToDoList.builder()
                .id(toDoListEntity.getId())
                .name(toDoListEntity.getName())
                .build();
    }

    public ToDoList toToDoListDtoWithIds(ToDoListEntity toDoListEntity) {
        return ToDoList.builder()
                .id(toDoListEntity.getId())
                .name(toDoListEntity.getName())
                .tasks(toDoListEntity.getTaskIds().stream()
                        .map(id->Task.builder()
                                .id(id)
                                .build())
                        .toList())
                .build();
    }

    public ToDoListEntity toToDoListEntity(ToDoList toDoList) {
        return ToDoListEntity.builder()
                .id(toDoList.id())
                .name(toDoList.name())
                .taskIds(toDoList.tasks()
                        .stream()
                        .map(Task::id)
                        .toList())
                .build();
    }
}
