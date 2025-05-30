package br.com.bitsincloud.clientservice.domain.repository;

import br.com.bitsincloud.clientservice.domain.entity.ClientLegal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientLegalRepository extends JpaRepository<ClientLegal, UUID> {
}
