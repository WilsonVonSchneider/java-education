package com.setronica.eventing.app.Payments;

import com.setronica.eventing.configuration.QueueConfiguration;
import com.setronica.eventing.dto.PaymentProviderResponse;
import com.setronica.eventing.persistence.Payments.PaymentRecord;
import com.setronica.eventing.persistence.Payments.PaymentStatus;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentProvider {
    @Autowired
    private RabbitTemplate template;

    @RabbitListener(queues = {"spring-boot"})
    public void paymentProviderSimulation(PaymentRecord message) {
        // In order to test with failed payment change PaymentStatus to FAILED
        // If real Payment provider is implemented here will be logic for it, instead we just simulate it like payment is authorized
        PaymentProviderResponse newPaymentProviderResponse = new PaymentProviderResponse(message.getId(), PaymentStatus.AUTHORIZED);

        template.convertAndSend(QueueConfiguration.paymentTopicExchangeName, QueueConfiguration.paymentRoutingKey, newPaymentProviderResponse);
    }
}