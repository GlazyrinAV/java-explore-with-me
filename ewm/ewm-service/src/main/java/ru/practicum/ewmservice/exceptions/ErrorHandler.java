package ru.practicum.ewmservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmcommondto.exceptions.*;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({CategoryNotFound.class, EventNotFound.class, CompilationNotFound.class,
            UserNotFound.class, ParticipationRequestNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse entityNotFound(RuntimeException exception) {
        return sendErrorResponse(exception.getMessage());
    }

    @ExceptionHandler({WrongParametr.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse wrongParametr(RuntimeException exception) {
        return sendErrorResponse(exception.getMessage());
    }

    private ErrorResponse sendErrorResponse(String description) {
        log.info(description);
        return new ErrorResponse(description);
    }

    @Data
    @AllArgsConstructor
    public static class ErrorResponse {
        private final String error;
    }

}