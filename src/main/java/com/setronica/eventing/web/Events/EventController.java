package com.setronica.eventing.web.Events;

import com.setronica.eventing.app.Events.EventService;
import com.setronica.eventing.dto.EventUpdate;
import com.setronica.eventing.persistence.Events.Event;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/events")
public class EventController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EventController.class);
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @Operation(tags = {"Events"}, summary = "Endpoint that creates new event")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public Event create(@Valid @RequestBody Event event) {
        return eventService.create(event);
    }

    @GetMapping("{id}")
    @Operation(tags = {"Events"}, summary = "Endpoint that returns event by id")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public Event show(
            @PathVariable Integer id
    ) {
        return eventService.findById(id);
    }

    @GetMapping
    @Operation(tags = {"Events"}, summary = "Endpoint that returns list of all events")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public List<Event> list() {
        return eventService.list();
    }

    @DeleteMapping("{id}")
    @Operation(tags = {"Events"}, summary = "Endpoint to delete event by id")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public void delete(@PathVariable Integer id) {
        Event existingEvent = eventService.findById(id);
        eventService.delete(existingEvent.getId());
    }

    @PutMapping("{id}")
    @Operation(tags = {"Event management"}, summary = "Updates existing event")
    @ApiResponse(responseCode = "404", description = "Event not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public Event update(@PathVariable Integer id, @RequestBody EventUpdate updatedEvent) {
        log.info("Request update event with id {}", id);
        Event existingEvent = eventService.findById(id);
        return eventService.update(updatedEvent, existingEvent);
    }


}