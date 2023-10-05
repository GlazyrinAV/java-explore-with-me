package ru.practicum.ewmclient.model.mark;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class MarkDto {

    Integer userId;

    Integer eventId;

    Integer mark;

}