package com.tuumsolutions.bankaccount.domain.transaction.events;

import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionMessageProducer {

    public static final String TRANSACTIONS_TOPIC_NAME = "tuum.transactions";

    private final RabbitTemplate rabbitTemplate;

    public void sendMsg(final TransactionMessage message) {
        log.debug("send message: {}", message);
        rabbitTemplate.convertAndSend(TRANSACTIONS_TOPIC_NAME, "", message);
    }
}
