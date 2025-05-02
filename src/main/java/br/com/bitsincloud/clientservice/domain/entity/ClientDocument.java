package br.com.bitsincloud.clientservice.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tb_client_document")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Clientes", description = "Operações relacionadas a clientes")
public class ClientDocument extends AuditDomain {

    @Schema(description = "ID do cliente", example = "123e4567-e89b-12d3-a456-426614174000")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Schema(description = "ID do cliente", example = "123e4567-e89b-12d3-a456-426614174000")
    @Column(name = "client_document_type")
    @Enumerated(EnumType.STRING)
    private ClientDocumentType type; // RG, CNH, FOTO, COMPROVANTE, etc.

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_url")
    private String fileUrl;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
