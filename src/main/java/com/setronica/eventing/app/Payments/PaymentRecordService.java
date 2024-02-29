package com.setronica.eventing.app.Payments;

import com.setronica.eventing.configuration.QueueConfiguration;
import com.setronica.eventing.exceptions.OrderAlreadyProcessed;
import com.setronica.eventing.persistence.Payments.PaymentRecord;
import com.setronica.eventing.persistence.Payments.PaymentRecordRepository;
import com.setronica.eventing.persistence.Payments.PaymentStatus;
import com.setronica.eventing.persistence.Payments.Status;
import com.setronica.eventing.persistence.Tickets.TicketOrder;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PaymentRecordService {

    @Autowired
    private RabbitTemplate template;

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PaymentRecordService.class);

    private final PaymentRecordRepository paymentRecordRepository;

    public PaymentRecordService(PaymentRecordRepository paymentRecordRepository) {
        this.paymentRecordRepository = paymentRecordRepository;
    }

    public void pay(TicketOrder existingTicketOrder) {
        if (existingTicketOrder.getStatus() != Status.BOOKED) {
            throw new OrderAlreadyProcessed("Order already processed");
        }

        PaymentRecord newPaymentRecord = new PaymentRecord();
        newPaymentRecord.setId(existingTicketOrder.getId());

        double total = BigDecimal.valueOf(existingTicketOrder.getAmount()).multiply(existingTicketOrder.getPrice()).doubleValue();

        newPaymentRecord.setTotal(BigDecimal.valueOf(total));
        newPaymentRecord.setStatus(PaymentStatus.PROCESSING);

        log.info("Creating payment record in DB");
        PaymentRecord savedPaymentRecord = paymentRecordRepository.save(newPaymentRecord);

        log.info("Send message to payment provider");
        template.convertAndSend(QueueConfiguration.topicExchangeName, QueueConfiguration.routingKey, savedPaymentRecord);
    }
}