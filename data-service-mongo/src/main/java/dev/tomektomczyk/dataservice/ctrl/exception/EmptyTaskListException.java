package dev.tomektomczyk.dataservice.ctrl.exception;

import lombok.Getter;

public class EmptyTaskListException extends RuntimeException {
    @Getter
    private final int errorCode;
    public EmptyTaskListException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
