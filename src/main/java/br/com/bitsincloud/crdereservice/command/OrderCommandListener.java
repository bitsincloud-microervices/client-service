package br.com.bitsincloud.crdereservice.command;

import br.com.bitsincloud.crdereservice.entity.Order;
import br.com.bitsincloud.crdereservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCommandListener {

    private final OrderRepository repository;

    @RabbitListener(queues = "command-create-order-queue")
    public void handleCommand(CreateOrderCommand command) {
        Order order = new Order();
        order.setOrderId(command.getOrderId());
        order.setAmount(command.getAmount());

        repository.save(order);
        System.out.println("Cliente salvo via comando: " + order);
    }
}
