package com.setronica.eventing.app.Events;

import com.setronica.eventing.dto.EventScheduleUpdate;
import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.persistence.Events.Event;
import com.setronica.eventing.persistence.Events.EventSchedule;
import com.setronica.eventing.persistence.Events.EventScheduleRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventScheduleService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(EventScheduleService.class);

    private final EventScheduleRepository eventScheduleRepository;

    public EventScheduleService(EventScheduleRepository eventRepository) {
        this.eventScheduleRepository = eventRepository;
    }

    public EventSchedule create(EventSchedule eventSchedule, Event exsistingEvent) {
        log.info("Creating new schedule for event in database");
        eventSchedule.setEventDate(exsistingEvent.getDate());
        eventSchedule.setEventId(exsistingEvent.getId());

        return eventScheduleRepository.save(eventSchedule);
    }

    public EventSchedule show(Integer id) {
        log.info("Fetching schedule of event from database by id: {}", id);

        return eventScheduleRepository.findById(id).orElseThrow(() -> new NotFoundException("Schedule of event not found. Event schedule id = " + id));
    }

    public List<EventSchedule> list() {
        log.info("Fetching all event schedules from database");

        return eventScheduleRepository.findAll();
    }

    public void delete(Integer id) {
        log.info("Deleting schedule for event from database by id: {}", id);

        eventScheduleRepository.deleteById(id);
    }

    public EventSchedule update(EventSchedule existingEventSchedule, EventScheduleUpdate eventScheduleUpdate) {
        log.info("Updating event schedule in database by id {}:", existingEventSchedule.getId());
        existingEventSchedule.setPrice(eventScheduleUpdate.getPrice());
        existingEventSchedule.setAvailableSeats(eventScheduleUpdate.getAvailableSeats());

        return eventScheduleRepository.save(existingEventSchedule);
    }
}