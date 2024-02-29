package com.setronica.eventing.app.Tickets;

import com.setronica.eventing.dto.TicketOrderUpdate;
import com.setronica.eventing.exceptions.NotEnoughSeats;
import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.persistence.Events.EventSchedule;
import com.setronica.eventing.persistence.Payments.Status;
import com.setronica.eventing.persistence.Tickets.TicketOrder;
import com.setronica.eventing.persistence.Tickets.TicketOrderRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketOrderService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TicketOrderService.class);

    private final TicketOrderRepository ticketOrderRepository;

    public TicketOrderService(TicketOrderRepository ticketOrderRepository) {
        this.ticketOrderRepository = ticketOrderRepository;
    }

    public TicketOrder create(TicketOrder ticketOrder, EventSchedule eventSchedule) {
        log.info("Creating ticket order in database");
        ticketOrder.setEventScheduleId(eventSchedule.getId());
        ticketOrder.setPrice(eventSchedule.getPrice());
        ticketOrder.setStatus(Status.BOOKED);

        try {
            return ticketOrderRepository.save(ticketOrder);
        } catch (Exception e) {
            throw new NotEnoughSeats("Not enough seats available");
        }
    }

    public TicketOrder show(Integer id) {
        log.info("Fetching ticket order from database by id: {}", id);

        return ticketOrderRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket order not found. Ticket order id is: " + id));
    }

    public List<TicketOrder> list() {
        log.info("Fetching all ticket orders from database");

        return ticketOrderRepository.findAll();
    }

    public void delete(Integer id) {
        log.info("Deleting ticket order from database by id: {}", id);
        ticketOrderRepository.deleteById(id);
    }

    public TicketOrder update(TicketOrder existingTicketOrder, TicketOrderUpdate ticketOrderUpdate) {
        log.info("Updating ticket order in database by id: {}", existingTicketOrder.getId());
        existingTicketOrder.setFirstname(ticketOrderUpdate.getFirstname());
        existingTicketOrder.setLastname(ticketOrderUpdate.getLastname());
        existingTicketOrder.setEmail(ticketOrderUpdate.getEmail());
        existingTicketOrder.setAmount(ticketOrderUpdate.getAmount());

        try {
            return ticketOrderRepository.save(existingTicketOrder);
        } catch (Exception e) {
            throw new NotEnoughSeats("Not enough seats available");
        }
    }
}