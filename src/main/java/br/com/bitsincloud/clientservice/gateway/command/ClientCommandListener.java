package br.com.bitsincloud.clientservice.gateway.command;

import br.com.bitsincloud.clientservice.domain.entity.Client;
import br.com.bitsincloud.clientservice.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientCommandListener {

    private final ClientRepository repository;

    @RabbitListener(queues = "command-create-client-queue")
    public void handleCommand(CreateClientCommand command) {
        Client client = new Client();
        client.setName(command.getName());
        client.setEmail(command.getEmail());
        client.setWhatsapp(command.getWhatsapp());

        repository.save(client);
        System.out.println("Cliente salvo via comando: " + client);
    }
}
