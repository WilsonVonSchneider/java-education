package com.setronica.eventing.web;

import com.setronica.eventing.app.EventService;
import com.setronica.eventing.persistence.Event;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("event/api/v1/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /**
     * Retrieves a list of events based on optional search criteria, sorting order, and parameter.
     *
     * @param search Optional search criteria to filter events by title or other attributes.
     * @param sort   The order of sorting ("ASC" for ascending, "DESC" for descending).
     *               Defaults to ascending order if not specified.
     * @param sortBy The parameter by which to sort the events ("title" or "date").
     *               Defaults to sorting by title if not specified.
     * @return A ResponseEntity containing the list of events based on the provided criteria.
     * Returns HTTP status OK (200) if successful.
     * <p>
     * This endpoint allows clients to retrieve a list of events with optional search criteria,
     * sorting order, and sorting parameter. The default sorting order is ascending, and the
     * default sorting parameter is the event title. The response contains the list of events
     * that match the specified criteria along with an HTTP status code indicating the result.
     */
    @GetMapping
    public ResponseEntity<List<Event>> list(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "sort", defaultValue = "ASC", required = false) String sort,
            @RequestParam(name = "sortBy", defaultValue = "title", required = false) String sortBy) {

        List<Event> events = eventService.list(search, sort, sortBy);

        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    /**
     * Retrieves details of a specific event based on its unique identifier.
     *
     * @param id The unique identifier of the event to retrieve.
     * @return A ResponseEntity containing the event details if found, or HTTP status NOT FOUND (404) if not.
     * <p>
     * This endpoint allows clients to retrieve details of a specific event by providing its unique identifier.
     * If the event with the specified ID is found, the response contains the event details along with an HTTP
     * status code indicating success. If no event is found with the given ID, the response returns HTTP status
     * NOT FOUND (404) along with an empty body.
     */
    @GetMapping("{id}")
    public ResponseEntity<Optional<Event>> show(@PathVariable Long id) {
        Optional<Event> event = eventService.show(id);

        return event.map(e -> ResponseEntity.ok().body(event))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(event));
    }


    /**
     * Creates a new event with the provided details.
     *
     * @param requestData The details of the new event to be created.
     * @return A ResponseEntity containing the created event details, or HTTP status BAD REQUEST (400) if validation fails.
     * <p>
     * This endpoint allows clients to create a new event by providing the necessary details in the request body.
     * The provided data is validated using the javax validation annotations, and if the data is valid, a new event
     * is created and returned in the response body along with an HTTP status code indicating success. If the provided
     * data is not valid, the response returns HTTP status BAD REQUEST (400) along with an error message.
     */
    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event requestData) {
        Event newEvent = eventService.create(requestData);

        return ResponseEntity.status(HttpStatus.OK).body(newEvent);
    }
}
