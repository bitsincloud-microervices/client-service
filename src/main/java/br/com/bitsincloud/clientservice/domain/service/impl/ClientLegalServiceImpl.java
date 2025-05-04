package br.com.bitsincloud.clientservice.domain.service.impl;

import br.com.bitsincloud.clientservice.api.model.request.ClientLegalRequest;
import br.com.bitsincloud.clientservice.api.model.response.ClientLegalResponse;
import br.com.bitsincloud.clientservice.domain.entity.ClientLegal;
import br.com.bitsincloud.clientservice.domain.repository.ClientLegalRepository;
import br.com.bitsincloud.clientservice.domain.service.ClientLegalService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientLegalServiceImpl implements ClientLegalService {

    private final ClientLegalRepository clientLegalRepository;

    @Override
    public ClientLegal save(ClientLegal clientLegal) {
        ClientLegal saved = clientLegalRepository.save(clientLegal);
        log.info("Cliente jurídico salvo com sucesso: {}", saved);

        if (saved.getId() != null) {
            log.info("Cliente jurídico com ID {} salvo com sucesso", saved.getId());
            return saved;
        } else {
            log.error("Erro ao salvar cliente jurídico");
        }

        return null;
    }

    @Override
    public Optional<ClientLegal> findById(UUID id) {
        Optional<ClientLegal> byId = clientLegalRepository.findById(id);

        if (byId.isPresent()) {
            log.info("Cliente jurídico encontrado com ID {}", id);
            return byId;
        } else {
            log.warn("Cliente jurídico com ID {} não encontrado", id);
            return Optional.empty();
        }
    }

    @Override
    public List<ClientLegal> findAll() {
        List<ClientLegal> all = clientLegalRepository.findAll();
        log.info("Total de clientes jurídicos encontrados: {}", all.size());

        if (all.isEmpty()) {
            log.warn("Nenhum cliente jurídico encontrado");
        } else {
            log.info("Clientes jurídicos encontrados: {}", all);
        }

        return all;
    }

    @Override
    public ClientLegal update(UUID id, ClientLegal clientLegal) {
        ClientLegal existing = clientLegalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente com ID " + id + " não encontrado"));

        // Atualizar campos
        existing.setCnpj(clientLegal.getCnpj());
        existing.setCompanyName(clientLegal.getCompanyName());
        existing.setBirthDate(clientLegal.getBirthDate());
        existing.setTradingName(clientLegal.getTradingName());

        return clientLegalRepository.save(existing);
    }

    @Override
    public void deleteById(UUID id) {
        if (!clientLegalRepository.existsById(id)) {
            throw new EntityNotFoundException("Cliente com ID " + id + " não encontrado para exclusão");
        }
        clientLegalRepository.deleteById(id);
    }
}
