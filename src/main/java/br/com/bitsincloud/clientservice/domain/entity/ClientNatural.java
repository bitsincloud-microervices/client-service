package br.com.bitsincloud.clientservice.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "tb_client_natural")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Client Natural", description = "Operações relacionadas a clientes")
public class ClientNatural extends Client {

    @Schema(description = "Nome da mãe do cliente", example = "Maria da Silva")
    @Column(name = "full_name")
    private String name;

    @Schema(description = "Email do cliente", example = "root@localhost")
    @Email(message = "{client.email.invalid}")
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
    private Gender gender;

    private LocalDate birthDate;

    @Schema(description = "RG do cliente", example = "12.345.678-9")
    private String rg;

    @Schema(description = "Nome fantasia da empresa", example = "Bits In Cloud")
    private String motherName;

    @Schema(description = "Nome fantasia da empresa", example = "Bits In Cloud")
    private String fatherName;
}
