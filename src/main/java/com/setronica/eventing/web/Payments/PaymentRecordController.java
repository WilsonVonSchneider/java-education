package com.setronica.eventing.web.Payments;

import com.setronica.eventing.app.Payments.PaymentRecordService;
import com.setronica.eventing.app.Tickets.TicketOrderService;
import com.setronica.eventing.persistence.Tickets.TicketOrder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("event/api/v1/events/event_schedules/ticket_orders")
public class PaymentRecordController {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PaymentRecordController.class);
    private final PaymentRecordService paymentRecordService;
    private final TicketOrderService ticketOrderService;

    public PaymentRecordController(TicketOrderService ticketOrderService, PaymentRecordService paymentRecordService) {
        this.ticketOrderService = ticketOrderService;
        this.paymentRecordService = paymentRecordService;
    }

    @PostMapping("{id}/pay")
    @Operation(tags = {"Payment record"}, summary = "Endpoint that tries to execute payment and creates payment record in database")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public void pay(@PathVariable Integer id) {
        TicketOrder existingTicketOrder = ticketOrderService.show(id);

        paymentRecordService.pay(existingTicketOrder);
    }

}