package br.com.bitsincloud.clientservice.repository;

import br.com.bitsincloud.clientservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {}
