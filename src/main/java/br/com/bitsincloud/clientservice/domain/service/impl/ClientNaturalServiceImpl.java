package br.com.bitsincloud.clientservice.domain.service.impl;

import br.com.bitsincloud.clientservice.api.mapper.ClientNaturalMapper;
import br.com.bitsincloud.clientservice.api.model.request.ClientNaturalRequest;
import br.com.bitsincloud.clientservice.api.model.response.ClientNaturalResponse;
import br.com.bitsincloud.clientservice.domain.entity.ClientNatural;
import br.com.bitsincloud.clientservice.domain.entity.Gender;
import br.com.bitsincloud.clientservice.domain.repository.ClientNaturalRepository;
import br.com.bitsincloud.clientservice.domain.service.ClientNaturalService;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientNaturalServiceImpl implements ClientNaturalService {

    private final ClientNaturalRepository clientNaturalRepository;
    private final ClientNaturalMapper clientNaturalMapper;

    @Override
    public ClientNaturalResponse save(ClientNaturalRequest clientNaturalRequest) {
        ClientNatural clientNatural = clientNaturalMapper.toEntity(clientNaturalRequest);
        ClientNatural saved = clientNaturalRepository.save(clientNatural);
        return clientNaturalMapper.toDTO(saved);
    }

    @Override
    public Optional<ClientNaturalResponse> findById(UUID id) {
        Optional<ClientNatural> byId = clientNaturalRepository.findById(id);
        if (byId.isPresent()) {
            return Optional.of(clientNaturalMapper.toDTO(byId.get()));
        } else {
            log.warn("Cliente com ID {} não encontrado", id);
            return Optional.empty();
        }
    }

    @Override
    public List<ClientNaturalResponse> findAll() {
        List<ClientNatural> all = clientNaturalRepository.findAll();
        return all.stream()
                .map(clientNaturalMapper::toDTO)
                .toList();
    }

    @Override
    public ClientNaturalResponse update(UUID id, ClientNaturalRequest clientNaturalRequest) {
        ClientNatural existing = clientNaturalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado"));

        ClientNatural clientNatural = clientNaturalMapper.toEntity(clientNaturalRequest);

        // Atualizar campos
        existing.setRg(clientNatural.getRg());
        existing.setCpf(clientNatural.getCpf());
        existing.setGender(clientNatural.getGender());
        existing.setBirthDate(clientNatural.getBirthDate());
        existing.setMotherName(clientNatural.getMotherName());
        existing.setFatherName(clientNatural.getFatherName());

        ClientNatural saved = clientNaturalRepository.save(existing);
        return clientNaturalMapper.toDTO(saved);
    }

    @Override
    public void deleteById(UUID id) {
        if (!clientNaturalRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente com ID " + id + " não encontrado para exclusão");
        }
        clientNaturalRepository.deleteById(id);
    }
}
