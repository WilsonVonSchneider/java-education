package com.setronica.eventing.app;

import com.setronica.eventing.persistence.Event;
import com.setronica.eventing.persistence.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class EventService {


    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> list(String search, String sort, String sortBy) {
        List<Event> events = eventRepository.getAll();

        if (search != null) {
            return events.stream()
                    .filter(event -> event.getTitle().toLowerCase().contains(search.toLowerCase()))
                    .collect(Collectors.toList());
        }

        return Event.sort(events, sortBy, sort);
    }

    public Optional<Event> show(Long id) {
        List<Event> events = eventRepository.getAll();

        return events.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }

    /**
     * This method serves as a demonstration for storing data to a JSON file. In a real-world scenario,
     * the file, located in the resources/static directory, is read-only. Consequently, this method
     * returns the newly created event, but it will not actually be added to the JSON file.
     */
    public Event create(Event requestData) {
        Event createEvent = new Event();
        createEvent.setId(new Random().nextInt(101));
        createEvent.setTitle(requestData.getTitle());
        createEvent.setDate(requestData.getDate());
        createEvent.setDescription(requestData.getDescription());
        createEvent.setImages(Event.getDemoImages());

        // Read existing events from the JSON file
        List<Event> existingEvents = eventRepository.getAll();

        // Add the new event to the list
        existingEvents.add(createEvent);


        eventRepository.create(existingEvents);

        return createEvent;
    }
}
