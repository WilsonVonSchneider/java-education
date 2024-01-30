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

    /**
     * Retrieves a list of all events without any optional criteria.
     *
     * @return A list containing all events.
     * <p>
     * This endpoint allows clients to retrieve a complete list of all events.
     * The response contains the list of events.
     */
    @GetMapping
    public List<Event> list() {
        return eventService.list();
    }

    /**
     * Retrieves a specific event by its unique identifier.
     *
     * @param id The unique identifier of the event.
     * @return The event with the specified ID.
     * @throws EntityNotFoundException if no event is found with the given ID.
     *                                 <p>
     *                                 This endpoint allows clients to retrieve details about a specific event by
     *                                 providing its unique identifier. If the event with the specified ID is found,
     *                                 it is returned. Otherwise, an EntityNotFoundException is thrown.
     */
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

    /**
     * Creates a new event based on the provided data.
     *
     * @param event The event object containing the data for the new event.
     * @return The created event.
     * <p>
     * This endpoint allows clients to create a new event by providing the necessary data in the request body.
     * The provided event data is validated using the @Valid annotation.
     * If the data is valid, the new event is created and returned.
     */
    @PostMapping
    public Event create(@Valid @RequestBody Event event) {
        return eventService.save(event);
    }

    /**
     * Updates an existing event with the provided data.
     *
     * @param id    The unique identifier of the event to be updated.
     * @param event The event object containing the updated data.
     * @return The updated event.
     * <p>
     * This endpoint allows clients to update an existing event by providing the unique identifier
     * of the event to be updated and the updated data in the request body.
     * If the event with the specified ID exists, it is updated with the provided data and returned.
     */
    @PutMapping("{id}")
    public Event update(@PathVariable int id, @RequestBody Event event) {
        return eventService.update(id, event);
    }

    /**
     * Deletes an existing event based on its unique identifier.
     *
     * @param id The unique identifier of the event to be deleted.
     * @throws EntityNotFoundException if no event is found with the given ID.
     *                                 <p>
     *                                 This endpoint allows clients to delete an existing event by providing
     *                                 its unique identifier. If the event with the specified ID exists, it is deleted.
     *                                 Otherwise, an EntityNotFoundException is thrown.
     */
    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        Event event = eventService.show(id);

        if (event == null) {
            throw new EntityNotFoundException("Event with id " + id + " not found");
        }

        eventService.delete(id);
    }
}