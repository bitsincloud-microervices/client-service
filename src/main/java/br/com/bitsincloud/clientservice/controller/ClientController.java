package br.com.bitsincloud.clientservice.controller;

import br.com.bitsincloud.clientservice.dto.ClientRequestDTO;
import br.com.bitsincloud.clientservice.dto.ClientResponseDTO;
import br.com.bitsincloud.clientservice.entity.Client;
import br.com.bitsincloud.clientservice.repository.ClientRepository;
import br.com.bitsincloud.clientservice.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
//TODO: Add Swagger documentation
//TODO: Adjust to RFC about HTTP status codes RFC 9110
//TODO: RFC 7807 (Problem Details for HTTP APIs)
//TODO: Add exception handling
//TODO: Add logging
//TODO: Add unit tests
public class ClientController {

    private final ClientService service;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        ClientResponseDTO clientResponseDTO = service.create(clientRequestDTO);
        return ResponseEntity.ok(clientResponseDTO);
    }

    @GetMapping
    public List<ClientResponseDTO> list() {
        return service.listAll();
    }
}
