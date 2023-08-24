package ru.practicum.ewmcommondto.exceptions;

public class EventNotFound extends RuntimeException {

    public  EventNotFound(String msg) {
        super(msg);
    }

    public EventNotFound(int eventId) {
        super("Событие с ID " + eventId + " не найдено.");
    }

}