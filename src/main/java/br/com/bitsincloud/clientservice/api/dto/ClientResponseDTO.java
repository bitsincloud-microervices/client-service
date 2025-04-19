package br.com.bitsincloud.clientservice.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.util.UUID;

@Data
@Tag(name = "Client Response", description = "Operações relacionadas a clientes")
public class ClientResponseDTO {

    @Schema(description = "ID do cliente", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID id;

    @Schema(description = "Nome do cliente", example = "João da Silva")
    private String name;

    @Schema(description = "Email do cliente", example = "root@localhost")
    private String email;

    @Schema(description = "Telefone do cliente", example = "(11) 91234-5678")
    private String whatsapp;
}
