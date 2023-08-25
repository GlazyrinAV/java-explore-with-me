package ru.practicum.ewmclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Hit {

    private String app;

    private String uri;

    private String ip;

    @JsonProperty("timestamp")
    private LocalDateTime timeStamp;

}