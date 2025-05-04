package br.com.bitsincloud.clientservice.api.mapper;

import br.com.bitsincloud.clientservice.api.model.request.ClientRequest;
import br.com.bitsincloud.clientservice.api.model.response.ClientResponse;
import br.com.bitsincloud.clientservice.domain.entity.Client;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequest dto) {
        Client client = new Client();
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        client.setWhatsapp(dto.getWhatsapp());
        return client;
    }

    public ClientResponse toDTO(Client entity) {
        ClientResponse dto = new ClientResponse();
        dto.setId(UUID.randomUUID());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        return dto;
    }
}
