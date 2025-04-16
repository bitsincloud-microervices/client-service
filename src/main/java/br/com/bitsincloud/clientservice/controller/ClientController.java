package br.com.bitsincloud.clientservice.controller;

import br.com.bitsincloud.clientservice.entity.Client;
import br.com.bitsincloud.clientservice.repository.ClientRepository;
import br.com.bitsincloud.clientservice.service.ClientEventPublisher;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientRepository repository;
    private final ClientEventPublisher publisher;

    @PostMapping
    public ResponseEntity<Client> create(@Valid @RequestBody Client client) {
        Client clientSaved = repository.save(client);
        publisher.publish(clientSaved);
        return ResponseEntity.ok(clientSaved);
    }

    @GetMapping
    public List<Client> list() {
        return repository.findAll();
    }
}
