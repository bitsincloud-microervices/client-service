package br.com.bitsincloud.clientservice.domain.repository;

import br.com.bitsincloud.clientservice.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {}
