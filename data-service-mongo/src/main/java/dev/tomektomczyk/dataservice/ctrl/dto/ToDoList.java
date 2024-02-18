package dev.tomektomczyk.dataservice.ctrl.dto;

import lombok.Builder;
import lombok.With;

import java.util.List;

@Builder
@With
public record ToDoList (
        String id,
        String name,
        List<Task> tasks) {}
