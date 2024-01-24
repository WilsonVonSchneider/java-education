package com.setronica.eventing.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

@Component
public class EventRepository {

    private final ObjectMapper objectMapper;
    private static final String JSON_FILE_PATH = "/static/events.json";


    public EventRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Event> getAll() {
        try (InputStream inputStream = EventRepository.class.getResourceAsStream(JSON_FILE_PATH)) {
            if (inputStream == null) {
                return Collections.emptyList();
            }

            return objectMapper.readValue(inputStream, EventList.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void create(List<Event> events) {
        try {
            String json = objectMapper.writeValueAsString(events);
            Path filePath = Path.of(getClass().getResource(JSON_FILE_PATH).toURI());
            Files.writeString(filePath, json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
