package ru.practicum.ewmservice.exceptions.exceptions;

public class EventNotFound extends RuntimeException {

    public  EventNotFound(String msg) {
        super(msg);
    }

    public EventNotFound(int eventId) {
        super("Событие с ID " + eventId + " не найдено.");
    }

}