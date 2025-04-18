package br.com.bitsincloud.clientservice.gateway;

import br.com.bitsincloud.clientservice.entity.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientEventPublisher {


    @Value("${spring.rabbitmq.publisher.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.publisher.routing-key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void publish(Client client) {
        rabbitTemplate.convertAndSend(exchange, routingKey, client);
    }
}
