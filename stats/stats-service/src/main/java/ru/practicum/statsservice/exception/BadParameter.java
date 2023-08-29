package ru.practicum.statsservice.exception;

public class BadParameter extends RuntimeException {

    public BadParameter(String msg) {
        super(msg);
    }

}
