package br.com.bitsincloud.clientservice.api.controller;

import br.com.bitsincloud.clientservice.api.mapper.ClientLegalMapper;
import br.com.bitsincloud.clientservice.api.model.request.ClientLegalRequest;
import br.com.bitsincloud.clientservice.api.model.response.ClientLegalResponse;
import br.com.bitsincloud.clientservice.domain.entity.ClientLegal;
import br.com.bitsincloud.clientservice.domain.service.ClientLegalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/clients/legal")
@RequiredArgsConstructor
@Tag(name = "Clientes Jurídicos", description = "Operações de CRUD para clientes jurídicos")
public class ClientLegalController {

    private static final Logger logger = LoggerFactory.getLogger(ClientLegalController.class);

    private final ClientLegalService clientLegalService;
    private final ClientLegalMapper clientLegalMapper;

    @PostMapping
    public ResponseEntity<ClientLegalResponse> create(@RequestBody ClientLegalRequest clientLegalRequest) {
        ClientLegal clientLegal = clientLegalMapper.toEntity(clientLegalRequest);
        ClientLegal created = clientLegalService.save(clientLegal);

        logger.info("Cliente criado com sucesso: {}", created);
        URI location = URI.create(String.format("/v1/clients/%s", created.getId()));
        ClientLegalResponse response = clientLegalMapper.toDTO(created);
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientLegalResponse> getById(@PathVariable UUID id) {
        Optional<ClientLegal> byId = clientLegalService.findById(id);
        if (byId.isPresent()) {
            ClientLegalResponse response = clientLegalMapper.toDTO(byId.get());
            return ResponseEntity.ok(response);
        } else {
            logger.warn("Cliente jurídico com ID {} não encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ClientLegalResponse>> getAll() {
        List<ClientLegal> clients = clientLegalService.findAll();
        if (clients.isEmpty()) {
            logger.warn("Nenhum cliente jurídico encontrado");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Clientes jurídicos encontrados: {}", clients);
            List<ClientLegalResponse> clientResponses = clientLegalMapper.toDTOList(clients);
            return ResponseEntity.ok(clientResponses);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientLegalResponse> update(@PathVariable UUID id, @RequestBody ClientLegalRequest clientLegalRequest) {
        ClientLegal clientLegal = clientLegalMapper.toEntity(clientLegalRequest);
        ClientLegal updated = clientLegalService.update(id, clientLegal);

        if (updated == null) {
            logger.warn("Cliente jurídico com ID {} não encontrado para atualização", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("Cliente jurídico atualizado com sucesso: {}", updated);
        ClientLegalResponse response = clientLegalMapper.toDTO(updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clientLegalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
