package com.setronica.eventing.web.Events;

import com.setronica.eventing.app.Events.EventScheduleService;
import com.setronica.eventing.app.Events.EventService;
import com.setronica.eventing.dto.EventScheduleUpdate;
import com.setronica.eventing.persistence.Events.Event;
import com.setronica.eventing.persistence.Events.EventSchedule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/events")
public class EventScheduleController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EventScheduleController.class);

    private final EventScheduleService eventScheduleService;
    private final EventService eventService;

    public EventScheduleController(EventScheduleService eventScheduleService, EventService eventService) {
        this.eventScheduleService = eventScheduleService;
        this.eventService = eventService;
    }

    @PostMapping("{id}/event_schedule")
    @Operation(tags = {"Event schedules"}, summary = "Endpoint that creates new schedule for event")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "400", description = "Not enough seats available")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public EventSchedule create(@PathVariable Integer id, @Valid @RequestBody EventSchedule eventSchedule) {
        Event existingEvent = eventService.findById(id);

        return eventScheduleService.create(eventSchedule, existingEvent);
    }

    @GetMapping("event_schedules/{id}")
    @Operation(tags = {"Event schedule"}, summary = "Endpoint that returns schedule of event by id")
    @ApiResponse(responseCode = "404", description = "Event schedule not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public EventSchedule show(
            @PathVariable Integer id
    ) {
        return eventScheduleService.show(id);
    }

    @GetMapping("event_schedules")
    @Operation(tags = {"Event schedules"}, summary = "Endpoint that returns a list of all event schedules")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public List<EventSchedule> list() {
        return eventScheduleService.list();
    }

    @DeleteMapping("event_schedules/{id}")
    @Operation(tags = {"Event schedule"}, summary = "Endpoint that deletes event schedule by id")
    @ApiResponse(responseCode = "404", description = "Event schedule not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public void delete(@PathVariable Integer id) {
        EventSchedule existingEvent = eventScheduleService.show(id);

        eventScheduleService.delete(existingEvent.getId());
    }

    @PutMapping("event_schedules/{id}")
    @Operation(tags = {"Event schedule"}, summary = "Endpoint that updates existing schedule of event by id")
    @ApiResponse(responseCode = "404", description = "Event schedule not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "400", description = "Not enough seats available")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public EventSchedule update(@PathVariable Integer id, @RequestBody EventScheduleUpdate eventScheduleUpdate) {
        EventSchedule existingScheduleEvent = eventScheduleService.show(id);

        return eventScheduleService.update(existingScheduleEvent, eventScheduleUpdate);
    }
}