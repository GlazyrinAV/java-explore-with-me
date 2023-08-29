package ru.practicum.ewmservice.exceptions.exceptions;

public class MarkNotFound extends RuntimeException {

    public MarkNotFound(String msg) {
        super(msg);
    }


    public MarkNotFound() {
        super("Оценка не найдена.");
    }

}