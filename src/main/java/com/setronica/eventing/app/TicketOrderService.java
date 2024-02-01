package com.setronica.eventing.app;

import com.setronica.eventing.dto.TicketOrderUpdate;
import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.persistence.TicketOrder;
import com.setronica.eventing.persistence.TicketOrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketOrderService {

    private final TicketOrderRepository ticketOrderRepository;

    public TicketOrderService(TicketOrderRepository ticketOrderRepository) {
        this.ticketOrderRepository = ticketOrderRepository;
    }

    public List<TicketOrder> getAll() {
        return ticketOrderRepository.findAll();
    }

    public TicketOrder findById(Integer id) {
        return ticketOrderRepository.findById(id).orElseThrow(() -> new NotFoundException("Ticket order not found with id=" + id));
    }

    public TicketOrder save(TicketOrder ticketOrder, int id) {
        ticketOrder.setEventScheduleId(id);
        
        return ticketOrderRepository.save(ticketOrder);
    }

    public TicketOrder update(TicketOrder existingTicketOrder, TicketOrderUpdate ticketOrderUpdate) {
        existingTicketOrder.setFirstname(ticketOrderUpdate.getFirstname());
        existingTicketOrder.setLastname(ticketOrderUpdate.getLastname());
        existingTicketOrder.setEmail(ticketOrderUpdate.getEmail());
        existingTicketOrder.setAmount(ticketOrderUpdate.getAmount());

        return ticketOrderRepository.save(existingTicketOrder);
    }

    public void delete(Integer id) {
        ticketOrderRepository.deleteById(id);
    }

}
