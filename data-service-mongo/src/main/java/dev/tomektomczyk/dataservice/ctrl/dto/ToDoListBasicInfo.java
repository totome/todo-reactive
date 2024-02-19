package dev.tomektomczyk.dataservice.ctrl.dto;

import lombok.Builder;
import lombok.With;

import java.util.List;

@Builder
@With
public record ToDoListBasicInfo(
        String id,
        String name,
        List<String> tasks) {}
