package com.example.clienteservice.command;

import com.example.clienteservice.entity.Client;
import com.example.clienteservice.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientCommandListener {

    private final ClientRepository repository;

    @RabbitListener(queues = "command-create-client-queue")
    public void handleCommand(CreateClientCommand command) {
        Client cliente = new Client();
        cliente.setName(command.getName());
        cliente.setEmail(command.getEmail());

        repository.save(cliente);
        System.out.println("Cliente salvo via comando: " + cliente);
    }
}
