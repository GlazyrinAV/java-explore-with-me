package ru.practicum.statsservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "stats", schema = "public")
@EqualsAndHashCode(of = {"id"})
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "app")
    private String app;

    @Column(name = "uri")
    private String uri;

    @Column(name = "ip")
    private String ip;

    @Column(name = "timestamp")
    @JsonProperty("timestamp")
    private LocalDateTime timeStamp;

}