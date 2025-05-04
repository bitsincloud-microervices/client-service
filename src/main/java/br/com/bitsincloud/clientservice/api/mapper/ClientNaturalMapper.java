package br.com.bitsincloud.clientservice.api.mapper;

import br.com.bitsincloud.clientservice.api.model.request.ClientNaturalRequest;
import br.com.bitsincloud.clientservice.api.model.response.ClientNaturalResponse;
import br.com.bitsincloud.clientservice.domain.entity.ClientNatural;
import br.com.bitsincloud.clientservice.domain.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class ClientNaturalMapper {

    public ClientNatural toEntity(ClientNaturalRequest request) {
        ClientNatural entity = new ClientNatural();
        entity.setName(request.getName()); // campo herdado de Client
        entity.setEmail(request.getEmail()); // campo herdado
        entity.setPhone(request.getPhone()); // campo herdado
        entity.setAddressId(UUID.randomUUID());
        entity.setWhatsapp("" + new Random().nextInt(1000000000) + 1);
        entity.setCpf(request.getCpf());

        Gender gender = Gender.fromString("MALE");
        entity.setGender(gender);
        entity.setBirthDate(request.getBirthDate());
        entity.setRg(request.getRg());
        entity.setMotherName(request.getMotherName());
        entity.setFatherName(request.getFatherName());

        return entity;
    }

    public ClientNaturalResponse toDTO(ClientNatural entity) {
        ClientNaturalResponse dto = new ClientNaturalResponse();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setWhatsapp(entity.getWhatsapp());
        dto.setPhone(entity.getPhone());
        dto.setAddressId(entity.getAddressId());
        dto.setGender(entity.getGender().getGender());
        dto.setBirthDate(entity.getBirthDate());
        dto.setCpf(entity.getCpf());
        dto.setRg(entity.getRg());
        dto.setMotherName(entity.getMotherName());
        dto.setFatherName(entity.getFatherName());
        return dto;
    }

    public List<ClientNaturalResponse> toDTOList(List<ClientNatural> clients) {
        return clients.stream()
                .map(this::toDTO)
                .toList();
    }
}
