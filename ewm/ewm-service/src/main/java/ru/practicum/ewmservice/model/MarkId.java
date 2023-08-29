package ru.practicum.ewmservice.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class MarkId implements Serializable {

    private User user;

    private Event event;

}