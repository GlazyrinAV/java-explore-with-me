package ru.practicum.ewmcommondto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EventRequestStatusUpdateResult {

    Collection<ParticipationRequestDto> confirmedRequests = new ArrayList<>();

    Collection<ParticipationRequestDto> rejectedRequests = new ArrayList<>();

}