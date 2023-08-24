package ru.practicum.ewmservice.controller.requests;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewmservice.service.requests.ParticipationRequestsPrivateService;

@RestController
@RequestMapping("/users/{usersId}/requests")
@RequiredArgsConstructor
@Slf4j
public class ParticipationRequestsPrivateController {

    private final ParticipationRequestsPrivateService service;

}