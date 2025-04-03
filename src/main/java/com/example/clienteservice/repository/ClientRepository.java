package com.example.clienteservice.repository;

import com.example.clienteservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {}
