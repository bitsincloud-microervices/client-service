package br.com.bitsincloud.clientservice.domain.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private String fullName;

    @Schema(description = "CPF do cliente", example = "123.456.789-00")
    private String cpf;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Schema(description = "Gênero do cliente", example = "MALE")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Schema(description = "RG do cliente", example = "12.345.678-9")
    @Column(name = "rg")
    private String rg;

    @Schema(description = "Nome da mãe do cliente", example = "Maria da Silva")
    @Column(name = "mother_name")
    private String motherName;

    @Schema(description = "Nome do pai do cliente", example = "José da Silva")
    @Column(name = "father_name")
    private String fatherName;
}
