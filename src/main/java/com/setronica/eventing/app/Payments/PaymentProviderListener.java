package com.setronica.eventing.app.Payments;

import com.setronica.eventing.dto.PaymentProviderResponse;
import com.setronica.eventing.exceptions.NotFoundException;
import com.setronica.eventing.persistence.Payments.PaymentRecord;
import com.setronica.eventing.persistence.Payments.PaymentRecordRepository;
import com.setronica.eventing.persistence.Payments.PaymentStatus;
import com.setronica.eventing.persistence.Payments.Status;
import com.setronica.eventing.persistence.Tickets.TicketOrder;
import com.setronica.eventing.persistence.Tickets.TicketOrderRepository;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentProviderListener {
    // listen to message from simulated payment provider and update ticket order status and payment record status accordingly
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PaymentRecordService.class);
    @Autowired
    private TicketOrderRepository ticketOrderRepository;

    @Autowired
    private PaymentRecordRepository paymentRecordRepository;

    @RabbitListener(queues = {"payment_queue"})
    public void paymentListener(PaymentProviderResponse message) {
        // update payment record status with status returned by payment provider
        PaymentRecord existingPaymentRecord = paymentRecordRepository.findById(message.getPaymentId()).orElseThrow(() -> new NotFoundException("Payment record not found by id=" + message.getPaymentId()));
        existingPaymentRecord.setStatus(message.getPaymentStatus());

        paymentRecordRepository.save(existingPaymentRecord);

        // update ticket order status according to status returned by payment provider
        TicketOrder existingTicketOrder = ticketOrderRepository.findById(message.getPaymentId()).orElseThrow(() -> new NotFoundException("Ticket order not found by id=" + message.getPaymentId()));
        Status newStatus = message.getPaymentStatus().equals(PaymentStatus.FAILED) ? Status.FAILED : Status.SALE;
        existingTicketOrder.setStatus(newStatus);

        log.info("Updating status of existing order");
        ticketOrderRepository.save(existingTicketOrder);
    }
}