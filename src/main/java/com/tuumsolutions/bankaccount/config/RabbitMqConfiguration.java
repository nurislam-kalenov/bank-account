package com.tuumsolutions.bankaccount.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.tuumsolutions.bankaccount.domain.transaction.events.TransactionMessageProducer.TRANSACTIONS_TOPIC_NAME;

@Configuration
public class RabbitMqConfiguration {

    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(TRANSACTIONS_TOPIC_NAME);
    }

    @Bean
    public AnonymousQueue deleteQueue1() {
        return new AnonymousQueue();
    }

    @Bean
    public AnonymousQueue deleteQueue2() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding1(FanoutExchange fanout, AnonymousQueue deleteQueue1) {
        return BindingBuilder.bind(deleteQueue1).to(fanout);
    }

    @Bean
    public Binding binding2(FanoutExchange fanout, AnonymousQueue deleteQueue2) {
        return BindingBuilder.bind(deleteQueue2).to(fanout);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
