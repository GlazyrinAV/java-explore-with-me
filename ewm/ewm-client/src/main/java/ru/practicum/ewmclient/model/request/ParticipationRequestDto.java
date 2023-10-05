package ru.practicum.ewmclient.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {

    private int id;

    private Integer event;

    private String created;

    private Integer requester;

    private String status;

}