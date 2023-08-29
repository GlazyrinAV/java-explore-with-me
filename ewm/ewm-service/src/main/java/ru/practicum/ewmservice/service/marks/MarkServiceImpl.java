package ru.practicum.ewmservice.service.marks;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewmclient.model.MarkDto;
import ru.practicum.ewmservice.exceptions.exceptions.*;
import ru.practicum.ewmservice.model.Event;
import ru.practicum.ewmservice.model.EventState;
import ru.practicum.ewmservice.model.Mark;
import ru.practicum.ewmservice.model.User;
import ru.practicum.ewmservice.model.mapper.MarkMapper;
import ru.practicum.ewmservice.repository.EventRepository;
import ru.practicum.ewmservice.repository.MarkRepository;
import ru.practicum.ewmservice.repository.ParticipationRequestsRepository;
import ru.practicum.ewmservice.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MarkServiceImpl implements MarkService {

    private final MarkRepository repository;

    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final ParticipationRequestsRepository requestsRepository;

    private final MarkMapper mapper;

    @Override
    public MarkDto save(int userId, int eventId, int mark) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFound(eventId));
        if (!event.getState().equals(EventState.PUBLISHED) || event.getEventDate().isAfter(LocalDateTime.now())) {
            throw new WrongParameter("Оценивать можно только опубликованные события, которые прошли.");
        }
        if (requestsRepository.findRequestByUserForMarks(userId, eventId) == null) {
            throw new WrongParameter("Оценку может ставить пользователь принимавший участие в событии.");
        }
        if (event.getInitiator().getId() == userId) {
            throw new WrongParameter("Автор мероприятия не может ставить оценку.");
        }
        if (mark < 1 || mark > 5) {
            throw new BadParameter("Оценка должна быть в пределах от 1 до 5.");
        }

        Mark eventMark = Mark.builder()
                .user(user)
                .event(event)
                .mark(mark)
                .build();
        return mapper.toDto(repository.save(eventMark));
    }

    @Override
    public void remove(int userId, int eventId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFound(userId));
        eventRepository.findById(eventId).orElseThrow(() -> new EventNotFound(eventId));
        Mark mark = repository.findByUser_IdAndEvent_Id(userId, eventId);
        if (mark == null) {
            throw new MarkNotFound();
        }
        repository.removeByUser_IdAndEvent_Id(userId, eventId);
    }

}