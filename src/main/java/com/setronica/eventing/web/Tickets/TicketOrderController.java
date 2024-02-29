package com.setronica.eventing.web.Tickets;

import com.setronica.eventing.app.Events.EventScheduleService;
import com.setronica.eventing.app.Tickets.TicketOrderService;
import com.setronica.eventing.dto.TicketOrderUpdate;
import com.setronica.eventing.persistence.Events.EventSchedule;
import com.setronica.eventing.persistence.Tickets.TicketOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("event/api/v1/events/event_schedules")
public class TicketOrderController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TicketOrderController.class);
    private final TicketOrderService ticketOrderService;
    private final EventScheduleService eventScheduleService;

    public TicketOrderController(TicketOrderService ticketOrderService, EventScheduleService eventScheduleService) {
        this.ticketOrderService = ticketOrderService;
        this.eventScheduleService = eventScheduleService;
    }

    @PostMapping("{id}/ticket_orders")
    @Operation(tags = {"Ticket orders"}, summary = "Endpoint that creates new ticket order")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public TicketOrder create(@PathVariable Integer id, @Valid @RequestBody TicketOrder ticketOrder) {
        EventSchedule existingEventSchedule = eventScheduleService.show(id);

        return ticketOrderService.create(ticketOrder, existingEventSchedule);
    }

    @GetMapping("ticket_orders/{id}")
    @Operation(tags = {"Ticket orders"}, summary = "Endpoint that returns ticket order by id")
    @ApiResponse(responseCode = "404", description = "Ticket order not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public TicketOrder show(
            @PathVariable Integer id
    ) {
        return ticketOrderService.show(id);
    }

    @GetMapping("ticket_orders")
    @Operation(tags = {"Ticket orders"}, summary = "Endpoint that returns a list of all ticket orders")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public List<TicketOrder> all() {
        return ticketOrderService.list();
    }

    @DeleteMapping("ticket_orders/{id}")
    @Operation(tags = {"Ticket orders"}, summary = "Endpoint that deletes ticket order")
    @ApiResponse(responseCode = "404", description = "Ticket order not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public void delete(@PathVariable Integer id) {
        TicketOrder existingTicketOrder = ticketOrderService.show(id);

        ticketOrderService.delete(existingTicketOrder.getId());
    }

    @PutMapping("ticket_orders/{id}")
    @Operation(tags = {"Ticket orders"}, summary = "Updates existing ticket order")
    @ApiResponse(responseCode = "404", description = "Ticket order not found")
    @ApiResponse(responseCode = "400", description = "Bad request")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public TicketOrder update(@PathVariable Integer id, @RequestBody TicketOrderUpdate ticketOrderUpdate) {
        TicketOrder existingTicketOrder = ticketOrderService.show(id);

        return ticketOrderService.update(existingTicketOrder, ticketOrderUpdate);
    }


}