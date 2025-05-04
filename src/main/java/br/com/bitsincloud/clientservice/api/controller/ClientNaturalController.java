package br.com.bitsincloud.clientservice.api.controller;

import br.com.bitsincloud.clientservice.api.mapper.ClientNaturalMapper;
import br.com.bitsincloud.clientservice.api.model.request.ClientNaturalRequest;
import br.com.bitsincloud.clientservice.api.model.response.ClientNaturalResponse;
import br.com.bitsincloud.clientservice.domain.entity.ClientNatural;
import br.com.bitsincloud.clientservice.domain.service.ClientNaturalService;
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
@RequestMapping("/v1/clients/natural")
@RequiredArgsConstructor
@Tag(name = "Clientes Jurídicos", description = "Operações de CRUD para clientes jurídicos")
public class ClientNaturalController {

    private static final Logger logger = LoggerFactory.getLogger(ClientNaturalController.class);

    private final ClientNaturalService clientNaturalService;
    private final ClientNaturalMapper clientNaturalMapper;

    @PostMapping
    public ResponseEntity<ClientNaturalResponse> create(@RequestBody ClientNaturalRequest clientNaturalRequest) {
        ClientNatural clientNatural = clientNaturalMapper.toEntity(clientNaturalRequest);
        ClientNatural created = clientNaturalService.save(clientNatural);

        logger.info("Cliente criado com sucesso: {}", created);
        URI location = URI.create(String.format("/v1/clients/%s", created.getId()));
        ClientNaturalResponse response = clientNaturalMapper.toDTO(created);
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientNaturalResponse> getById(@PathVariable UUID id) {
        Optional<ClientNatural> byId = clientNaturalService.findById(id);
        if (byId.isPresent()) {
            ClientNaturalResponse response = clientNaturalMapper.toDTO(byId.get());
            return ResponseEntity.ok(response);
        } else {
            logger.warn("Cliente jurídico com ID {} não encontrado", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ClientNaturalResponse>> getAll() {
        List<ClientNatural> clients = clientNaturalService.findAll();
        if (clients.isEmpty()) {
            logger.warn("Nenhum cliente jurídico encontrado");
            return ResponseEntity.noContent().build();
        } else {
            logger.info("Clientes jurídicos encontrados: {}", clients);
            List<ClientNaturalResponse> clientResponses = clientNaturalMapper.toDTOList(clients);
            return ResponseEntity.ok(clientResponses);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientNaturalResponse> update(@PathVariable UUID id, @RequestBody ClientNaturalRequest clientNaturalRequest) {
        ClientNatural clientNatural = clientNaturalMapper.toEntity(clientNaturalRequest);
        ClientNatural updated = clientNaturalService.update(id, clientNatural);

        if (updated == null) {
            logger.warn("Cliente jurídico com ID {} não encontrado para atualização", id);
            return ResponseEntity.notFound().build();
        }
        logger.info("Cliente jurídico atualizado com sucesso: {}", updated);
        ClientNaturalResponse response = clientNaturalMapper.toDTO(updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        clientNaturalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
