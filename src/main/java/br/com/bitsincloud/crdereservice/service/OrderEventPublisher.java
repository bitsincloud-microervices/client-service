package br.com.bitsincloud.crdereservice.service;

import br.com.bitsincloud.crdereservice.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventPublisher {


    @Value("${spring.rabbitmq.publisher.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.publisher.routing-key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void publish(Order client) {
        rabbitTemplate.convertAndSend(exchange, routingKey, client);
    }
}
