package com.example.clienteservice.controller;

import com.example.clienteservice.entity.Client;
import com.example.clienteservice.repository.ClientRepository;
import com.example.clienteservice.service.ClientEventPublisher;
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
