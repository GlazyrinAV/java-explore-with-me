package ru.practicum.ewmservice.service.statsrequest;

import java.util.Collection;
import java.util.LinkedHashMap;

public interface StatsRequestService {

    void updateViews(Collection<LinkedHashMap<String, Object>> viewsData);

}