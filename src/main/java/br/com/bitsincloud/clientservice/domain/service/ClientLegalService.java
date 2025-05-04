package br.com.bitsincloud.clientservice.domain.service;

import br.com.bitsincloud.clientservice.domain.entity.ClientLegal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface  ClientLegalService {
    ClientLegal save(ClientLegal clientLegal);
    Optional<ClientLegal> findById(UUID id);
    List<ClientLegal> findAll();
    ClientLegal update(UUID id, ClientLegal clientLegal);
    void deleteById(UUID id);
}
