package com.setronica.eventing.app;

import com.setronica.eventing.persistence.Event;
import com.setronica.eventing.persistence.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> list() {
        return eventRepository.findAll();
    }

    public Event show(int id) {
        return eventRepository.findById(id).orElse(null);
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Event update(int id, Event updateEvent) {
        Event event = eventRepository.findById(id).orElse(null);

        if (event != null) {
            event.setTitle(updateEvent.getTitle());
            event.setDescription(updateEvent.getDescription());
            event.setDate(updateEvent.getDate());

            return eventRepository.save(event);
        } else {
            throw new EntityNotFoundException("Event with id " + id + " not found");
        }
    }


    public void delete(int id) {
        eventRepository.deleteById(id);
    }

}