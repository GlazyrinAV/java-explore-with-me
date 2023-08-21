package ru.practicum.statsclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.practicum.statscommondto.model.EndPointHit;

@SpringBootApplication
public class StatsClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatsClientApplication.class, args);
    }

}