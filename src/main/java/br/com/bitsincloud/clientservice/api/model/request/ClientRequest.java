package br.com.bitsincloud.clientservice.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Tag(name = "Client Request", description = "Operações relacionadas a clientes")
public class ClientRequest {

    @Schema(description = "Nome do cliente", example = "João da Silva")
    @NotBlank(message = "{client.name.not-null}")
    private String name;

    @Schema(description = "CPF do cliente", example = "123.456.789-00")
    @NotBlank(message = "{client.cpf.not-null}")
    private String cpf;

    @Schema(description = "Email do cliente", example = "root@localhost")
    @Email(message = "{client.email.invalid}")
    private String email;

    @Schema(description = "Telefone do cliente", example = "(11) 91234-5678")
    private String whatsapp;

    @Schema(description = "Endereço do cliente", example = "Rua das Flores, 123")
    @NotBlank(message = "{client.address.not-null}")
    private String address;
}
