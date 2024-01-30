package com.setronica.eventing.web;

import com.setronica.eventing.app.EventService;
import com.setronica.eventing.persistence.Event;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> list() {
        return eventService.list();
    }

    @GetMapping("{id}")
    public Event show(
            @PathVariable int id
    ) {
        Event event = eventService.show(id);

        if (event == null) {
            throw new EntityNotFoundException("Event with id " + id + " not found");
        }

        return event;
    }

    @PostMapping
    public Event create(@Valid @RequestBody Event event) {
        return eventService.save(event);
    }

    @PutMapping("{id}")
    public Event update(@PathVariable int id, @RequestBody Event event) {
        return eventService.update(id, event);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        Event event = eventService.show(id);

        if (event == null) {
            throw new EntityNotFoundException("Event with id " + id + " not found");
        }

        eventService.delete(id);
    }
}