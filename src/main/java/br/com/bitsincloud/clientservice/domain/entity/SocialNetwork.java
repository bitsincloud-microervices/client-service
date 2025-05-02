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
@Table(name = "tb_social_network")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "SocialNetwork", description = "Operações relacionadas a SocialNetwork dos clientes")
public class SocialNetwork extends AuditDomain {

    @Schema(description = "ID do cliente", example = "123e4567-e89b-12d3-a456-426614174000")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Schema(description = "Social network type", example = "LINKEDIN")
    @Enumerated(EnumType.STRING)
    @Column(name = "social_network_type", nullable = false)
    private SocialNetworkType type;

    private String url;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
