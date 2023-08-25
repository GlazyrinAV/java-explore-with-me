package ru.practicum.ewmcommondto.exceptions;

public class WrongParameter extends RuntimeException {

    public WrongParameter(String msg) {
        super(msg);
    }

}