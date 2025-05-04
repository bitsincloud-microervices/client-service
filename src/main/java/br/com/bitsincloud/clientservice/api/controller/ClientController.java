package br.com.bitsincloud.clientservice.api.controller;

import br.com.bitsincloud.clientservice.api.model.request.ClientRequest;
import br.com.bitsincloud.clientservice.api.model.response.ClientResponse;
import br.com.bitsincloud.clientservice.domain.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody ClientRequest clientRequest) {
        ClientResponse clientResponse = service.create(clientRequest);

        logger.info("Cliente criado com sucesso: {}", clientResponse);
        URI location = URI.create(String.format("/v1/clients/%s", clientResponse.getId()));
        return ResponseEntity.created(location).body(clientResponse);
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
    public List<ClientResponse> list() {
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
    public ResponseEntity<ClientResponse> getById(
            @Parameter(description = "ID do cliente", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadDocument(
            @PathVariable UUID id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("type") String type
    ) {
        try {
            String path = service.uploadFile(file, id, type);
            return ResponseEntity.ok("Arquivo salvo em: " + path);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }

}
