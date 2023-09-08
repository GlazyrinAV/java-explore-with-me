package ru.practicum.statsservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "Stats")
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "stats", schema = "public")
@EqualsAndHashCode(of = {"id"})
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Stats implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "timestamp")
    @JsonProperty("timestamp")
    private LocalDateTime timeStamp;

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    private JsonNode additionalProps;

}