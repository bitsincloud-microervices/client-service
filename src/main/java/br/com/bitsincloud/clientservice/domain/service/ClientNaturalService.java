package br.com.bitsincloud.clientservice.domain.service;

import br.com.bitsincloud.clientservice.domain.entity.ClientNatural;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientNaturalService {
    ClientNatural save(ClientNatural clientNatural);
    Optional<ClientNatural> findById(UUID id);
    List<ClientNatural> findAll();
    ClientNatural update(UUID id, ClientNatural clientNatural);
    void deleteById(UUID id);
}
