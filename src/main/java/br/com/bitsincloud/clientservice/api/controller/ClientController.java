package br.com.bitsincloud.clientservice.api.controller;

import br.com.bitsincloud.clientservice.api.dto.ClientRequestDTO;
import br.com.bitsincloud.clientservice.api.dto.ClientResponseDTO;
import br.com.bitsincloud.clientservice.domain.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

//TODO: Add Swagger documentation
//TODO: Adjust to RFC about HTTP status codes RFC 9110
//TODO: RFC 7807 (Problem Details for HTTP APIs)
//TODO: Add exception handling
//TODO: Add logging
//TODO: Add unit tests
@RestController
@RequestMapping("/v1/clients")
@RequiredArgsConstructor
@Tag(name = "Clients", description = "Operações relacionadas a clientes")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final ClientService service;

    @PostMapping
    @Operation(
            summary = "Cria um novo cliente",
            description = "Cria e retorna os dados do cliente criado",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<ClientResponseDTO> create(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        ClientResponseDTO clientResponseDTO = service.create(clientRequestDTO);

        logger.info("Cliente criado com sucesso: {}", clientResponseDTO);
        URI location = URI.create(String.format("/v1/clients/%s", clientResponseDTO.getId()));
        return ResponseEntity.created(location).body(clientResponseDTO);
    }

    @GetMapping
    @Operation(
            summary = "Busca cliente por ID",
            description = "Retorna os dados do cliente correspondente ao ID informado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
            }
    )
    public List<ClientResponseDTO> list() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Busca cliente por ID",
            description = "Retorna os dados do cliente com o ID informado",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
            }
    )
    public ResponseEntity<ClientResponseDTO> getById(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
