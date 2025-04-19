package br.com.bitsincloud.clientservice.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tb_client")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Clientes", description = "Operações relacionadas a clientes")
public class Client extends AuditDomain {

    @Schema(description = "ID do cliente", example = "123e4567-e89b-12d3-a456-426614174000")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Schema(description = "Nome do cliente", example = "João da Silva")
    @NotNull(message = "{client.name.not-null}")
    private String name;

    @Schema(description = "Email do cliente", example = "root@localhost")
    @Email(message = "{client.email.invalid}")
    private String email;

    @Schema(description = "Telefone do cliente", example = "(11) 91234-5678")
    private String whatsapp;
}
