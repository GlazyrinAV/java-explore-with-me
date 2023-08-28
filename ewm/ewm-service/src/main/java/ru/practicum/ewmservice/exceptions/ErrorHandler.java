package ru.practicum.ewmservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.ewmservice.exceptions.exceptions.*;

import javax.validation.ValidationException;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({CategoryNotFound.class, EventNotFound.class, CompilationNotFound.class,
            UserNotFound.class, ParticipationRequestNotFound.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse entityNotFound(RuntimeException exception) {
        return sendErrorResponse(exception.getMessage());
    }

    @ExceptionHandler({WrongParameter.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse wrongParameter(RuntimeException exception) {
        return sendErrorResponse(exception.getMessage());
    }

    @ExceptionHandler({BadParameter.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badParameter(RuntimeException exception) {
        return sendErrorResponse(exception.getMessage());
    }

    @ExceptionHandler({NoConfirmationNeeded.class})
    @ResponseStatus(HttpStatus.OK)
    public ErrorResponse noConfirmationNeeded(RuntimeException exception) {
        return sendErrorResponse(exception.getMessage());
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse dataIntegrityError(DataIntegrityViolationException exception) {
        return sendErrorResponse(Objects.requireNonNull(exception.getRootCause()).getMessage());
    }

    @ExceptionHandler({ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse validationError(ValidationException exception) {
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