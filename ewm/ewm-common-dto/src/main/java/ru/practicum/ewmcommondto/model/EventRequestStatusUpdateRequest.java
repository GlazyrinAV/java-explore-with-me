package ru.practicum.ewmcommondto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class EventRequestStatusUpdateRequest {

    Collection<Integer> requestIds;

    String status;

}