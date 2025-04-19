package br.com.bitsincloud.clientservice.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Tag(name = "Client Request", description = "Operações relacionadas a clientes")
public class ClientRequestDTO {

    @Schema(description = "Nome do cliente", example = "João da Silva")
    @NotBlank(message = "{client.name.not-null}")
    private String name;

    @Schema(description = "Email do cliente", example = "root@localhost")
    @Email(message = "{client.email.invalid}")
    private String email;

    @Schema(description = "Telefone do cliente", example = "(11) 91234-5678")
    private String whatsapp;
}
