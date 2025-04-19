package br.com.bitsincloud.clientservice.api.mapper;

import br.com.bitsincloud.clientservice.api.dto.ClientRequestDTO;
import br.com.bitsincloud.clientservice.api.dto.ClientResponseDTO;
import br.com.bitsincloud.clientservice.domain.entity.Client;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ClientMapper {

    public Client toEntity(ClientRequestDTO dto) {
        Client client = new Client();
        client.setName(dto.getName());
        client.setEmail(dto.getEmail());
        return client;
    }

    public ClientResponseDTO toDTO(Client entity) {
        ClientResponseDTO dto = new ClientResponseDTO();
        dto.setId(UUID.randomUUID());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        return dto;
    }
}
