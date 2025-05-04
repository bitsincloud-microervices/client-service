package br.com.bitsincloud.clientservice.api.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Tag(name = "Client Request", description = "Operações relacionadas a clientes")
public class ClientNaturalRequest {
    
    @Schema(description = "Nome do cliente", example = "João da Silva")
    private String name;

    @Schema(description = "Email do cliente", example = "root@localhost")
    private String email;

    @Schema(description = "Telefone do cliente", example = "(11) 91234-5678")
    private String whatsapp;

    @Schema(description = "CPF do cliente", example = "123.456.789-00")
    private String phone;

    @Schema(description = "ID do Edereço no sistema de terceiros", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID addressId;

    @Schema(description = "CNPJ do cliente", example = "12.345.678/0001-90")
    private String cpf;

    @Schema(description = "Nome da empresa", example = "Bits In Cloud")
    private String gender;

    private LocalDate birthDate;

    @Schema(description = "RG do cliente", example = "12.345.678-9")
    @Column(name = "rg")
    private String rg;

    @Schema(description = "Nome fantasia da empresa", example = "Bits In Cloud")
    private String motherName;

    @Schema(description = "Nome fantasia da empresa", example = "Bits In Cloud")
    private String fatherName;
}
