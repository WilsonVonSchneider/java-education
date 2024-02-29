package com.setronica.eventing.app.Events;

import com.setronica.eventing.dto.EventUpdate;
import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.persistence.Events.Event;
import com.setronica.eventing.persistence.Events.EventRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EventService.class);
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Event create(Event event) {
        log.info("Creating new event in database");

        return eventRepository.save(event);
    }

    public Event findById(Integer id) {
        log.info("Fetching event from the database by id: {}", id);

        return eventRepository.findById(id).orElseThrow(() -> new NotFoundException("Event not found. Event id = " + id));
    }

    public List<Event> list() {
        log.info("Fetching all events from database");

        return eventRepository.findAll();
    }

    public void delete(int id) {
        log.info("Deleting event from database by id: {}", id);

        eventRepository.deleteById(id);
    }

    public Event update(EventUpdate eventUpdate, Event existingEvent) {
        log.info("Updating event in database by id: {} ", existingEvent.getId());
        existingEvent.setTitle(eventUpdate.getTitle());
        existingEvent.setDescription(eventUpdate.getDescription());
        existingEvent.setDate(eventUpdate.getDate());

        return eventRepository.save(existingEvent);
    }
}