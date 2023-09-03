package com.tuumsolutions.bankaccount.domain.transaction.events;

import com.tuumsolutions.bankaccount.domain.transaction.model.TransactionMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransactionMessageListener {

    @RabbitListener(queues = "#{deleteQueue1.name}")
    public void receive1(TransactionMessage message)   {
        log.info("transaction message deleteQueue1:  {}", message);
    }

    @RabbitListener(queues = "#{deleteQueue2.name}")
    public void receive2(TransactionMessage message)   {
        log.info("transaction message deleteQueue2:  {}", message);
    }

}
