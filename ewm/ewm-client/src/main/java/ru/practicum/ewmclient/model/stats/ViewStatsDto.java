package ru.practicum.ewmclient.model.stats;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ViewStatsDto {

    @JsonProperty("uri")
    private String uri;

    @JsonProperty("hits")
    private Integer hits;

}