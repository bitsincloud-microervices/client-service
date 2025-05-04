package br.com.bitsincloud.clientservice.api.mapper;

import br.com.bitsincloud.clientservice.api.model.request.ClientLegalRequest;
import br.com.bitsincloud.clientservice.api.model.request.ClientRequest;
import br.com.bitsincloud.clientservice.api.model.response.ClientLegalResponse;
import br.com.bitsincloud.clientservice.api.model.response.ClientResponse;
import br.com.bitsincloud.clientservice.domain.entity.Client;
import br.com.bitsincloud.clientservice.domain.entity.ClientDocumentType;
import br.com.bitsincloud.clientservice.domain.entity.ClientLegal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class ClientLegalMapper {

    public ClientLegal toEntity(ClientLegalRequest request) {
        ClientLegal entity = new ClientLegal();
        entity.setName(request.getName()); // campo herdado de Client
        entity.setEmail(request.getEmail()); // campo herdado
        entity.setPhone(request.getPhone()); // campo herdado
        entity.setAddressId(UUID.randomUUID());
        entity.setWhatsapp("" + new Random().nextInt(1000000000) + 1);

        entity.setCnpj(request.getCnpj());
        entity.setCompanyName(request.getCompanyName());
        entity.setBirthDate(request.getBirthDate());
        entity.setTradingName(request.getTradingName());

        return entity;
    }

    public ClientLegalResponse toDTO(Client entity) {
       ClientLegalResponse dto = new ClientLegalResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setWhatsapp(entity.getWhatsapp());
        dto.setCnpj(((ClientLegal) entity).getCnpj());
        dto.setCompanyName(((ClientLegal) entity).getCompanyName());
        dto.setBirthDate(((ClientLegal) entity).getBirthDate());
        dto.setTradingName(((ClientLegal) entity).getTradingName());

        return dto;
    }

    public List<ClientLegalResponse> toDTOList(List<ClientLegal> clients) {
        return clients.stream()
                .map(this::toDTO)
                .toList();
    }
}
