package br.com.bitsincloud.clientservice.domain.service;

import br.com.bitsincloud.clientservice.api.dto.ClientRequestDTO;
import br.com.bitsincloud.clientservice.api.dto.ClientResponseDTO;
import br.com.bitsincloud.clientservice.domain.entity.Client;
import br.com.bitsincloud.clientservice.api.exception.ClientNotFoundException;
import br.com.bitsincloud.clientservice.gateway.ClientEventPublisher;
import br.com.bitsincloud.clientservice.api.mapper.ClientMapper;
import br.com.bitsincloud.clientservice.domain.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//ervice é a camada responsável por orquestrar:
// - A lógica de negócio
// - A comunicação entre as camadas de Controller e Repository
// - A validação de dados
// - A transformação de dados entre DTOs e entidades
// - A publicação de eventos
// - A comunicação com serviços externos
// - A implementação de regras de negócio
// - A implementação de transações
// - A implementação de cache
@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Value("${client.publish-enabled:true}")
    private boolean publishEnabled;

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final ClientEventPublisher publisher;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper, ClientEventPublisher publisher) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
        this.publisher = publisher;
    }

    public ClientResponseDTO create(ClientRequestDTO requestDTO) {
        Client client = clientMapper.toEntity(requestDTO);
        client = clientRepository.save(client);

        if (publishEnabled) {
            logger.info("Publicando evento de criação do cliente: {}", client);
            publisher.publish(client);
        }

        logger.info("Cliente criado com sucesso: {}", client);
        return clientMapper.toDTO(client);
    }

    public List<ClientResponseDTO> listAll() {
        List<Client> clients = clientRepository.findAll();

        if (clients.isEmpty()) {
            throw new ClientNotFoundException("Nenhum cliente encontrado.");
        }

        logger.info("Encontrados {} clientes", clients.size());
        return clients.stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClientResponseDTO findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));

        logger.info("Cliente encontrado: {}", client);
        return clientMapper.toDTO(client);
    }
}
