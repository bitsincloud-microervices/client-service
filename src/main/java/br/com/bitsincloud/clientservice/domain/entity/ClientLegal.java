package br.com.bitsincloud.clientservice.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_client_legal")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "Clientes", description = "Operações relacionadas a clientes")
public class ClientLegal extends Client {

    @Schema(description = "CNPJ do cliente", example = "12.345.678/0001-90")
    @Column(name = "cnpj")
    private String cnpj;

    @Schema(description = "Nome da empresa", example = "Bits In Cloud")
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "foundation_date")
    private LocalDate birthDate;

    @Schema(description = "Nome fantasia da empresa", example = "Bits In Cloud")
    @Column(name = "trading_name")
    private String tradingName;
}
