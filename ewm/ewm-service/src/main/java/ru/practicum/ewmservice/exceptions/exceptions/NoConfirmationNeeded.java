package ru.practicum.ewmservice.exceptions.exceptions;

public class NoConfirmationNeeded extends RuntimeException {

    public NoConfirmationNeeded() {
        super("Для данного события не требуется подтверждение участия.");
    }

}