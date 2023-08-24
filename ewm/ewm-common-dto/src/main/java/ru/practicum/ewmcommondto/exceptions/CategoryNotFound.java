package ru.practicum.ewmcommondto.exceptions;

public class CategoryNotFound extends RuntimeException {

    public CategoryNotFound(String msg) {
        super(msg);
    }

    public CategoryNotFound(int catId) {
        super("Категория с ID " + catId + " не найдена.");
    }

}