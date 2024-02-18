package dev.tomektomczyk.dataservice.ctrl.dto;

import lombok.Builder;

@Builder
public record Task(
        String id,
        String name,
        boolean isDone) { }
