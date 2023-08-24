package ru.practicum.ewmcommondto.exceptions;

public class ParticipationRequestNotFound extends RuntimeException {

    public ParticipationRequestNotFound(String msg) {
        super(msg);
    }

    public ParticipationRequestNotFound(int partId) {
        super("Заявка с ID " + partId + " не найдена.");
    }

}
