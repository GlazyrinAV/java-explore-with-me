package ru.practicum.ewmservice.exceptions.exceptions;

public class CompilationNotFound extends RuntimeException {

    public CompilationNotFound(String msg) {
        super(msg);
    }

    public CompilationNotFound(int compId) {
        super("Подборка с ID " + compId + " не найдена.");
    }

}
