package ru.practicum.ewmservice.service.statsrequest;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.practicum.ewmservice.repository.EventRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.LinkedHashMap;

@Service
@RequiredArgsConstructor
public class StatsRequestServiceImpl implements StatsRequestService {

    private final EventRepository repository;

    @Transactional
    @KafkaListener(topics = "ewm.views.update", groupId = "ewmGroup")
    public void updateViews(Collection<LinkedHashMap<String, Object> > viewsData) {
        for (LinkedHashMap<String, Object> element : viewsData) {
                int eventId = Integer.parseInt(element.get("uri").toString());
                int views = (Integer) element.get("hits");
                repository.updateViews(eventId, views);
        }
    }

}