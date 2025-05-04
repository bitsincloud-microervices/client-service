package br.com.bitsincloud.clientservice.domain.service;

import br.com.bitsincloud.clientservice.api.model.request.ClientRequest;
import br.com.bitsincloud.clientservice.api.model.response.ClientResponse;
import br.com.bitsincloud.clientservice.domain.entity.Client;
import br.com.bitsincloud.clientservice.api.exception.ClientNotFoundException;
import br.com.bitsincloud.clientservice.domain.entity.ClientDocument;
import br.com.bitsincloud.clientservice.domain.entity.ClientDocumentType;
import br.com.bitsincloud.clientservice.domain.repository.DocumentRepository;
import br.com.bitsincloud.clientservice.gateway.AddressGateway;
import br.com.bitsincloud.clientservice.gateway.ClientEventPublisher;
import br.com.bitsincloud.clientservice.api.mapper.ClientMapper;
import br.com.bitsincloud.clientservice.domain.repository.ClientRepository;
import br.com.bitsincloud.clientservice.util.ValidateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
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
    public static final String UPLOAD_DIRECTORY = "uploads";
    public static final String SLASH = "/";

    @Value("${client.publish-enabled:true}")
    private boolean publishEnabled;

    private final ClientRepository clientRepository;
    private final DocumentRepository documentRepository;
    private final AddressGateway addressClient;
    private final ClientMapper clientMapper;
    private final ClientEventPublisher publisher;

    public ClientService(ClientRepository clientRepository, DocumentRepository documentRepository, AddressGateway addressClient, ClientMapper clientMapper, ClientEventPublisher publisher) {
        this.clientRepository = clientRepository;
        this.documentRepository = documentRepository;
        this.addressClient = addressClient;
        this.clientMapper = clientMapper;
        this.publisher = publisher;
    }

    public ClientResponse create(ClientRequest requestDTO) {
        if (!ValidateUtils.validateCpf(requestDTO.getCpf())) {
            throw new IllegalArgumentException("CPF inválido");
        }

        Client client = clientMapper.toEntity(requestDTO);

        //UUID addressId = addressClient.resolveStreetToId(requestDTO.getAddress());
        // if (addressId == null) {
        //     throw new IllegalArgumentException("Endereço inválido");
        // }

        UUID addressId = UUID.randomUUID();
        client.setAddressId(addressId);
        client.setPhone(String.valueOf(new Random().nextInt(1000000000) + 1));
        client = clientRepository.save(client);

        if (publishEnabled) {
            logger.info("Publicando evento de criação do cliente: {}", client);
            publisher.publish(client);
        }

        logger.info("Cliente criado com sucesso: {}", client);
        return clientMapper.toDTO(client);
    }

    public List<ClientResponse> listAll() {
        List<Client> clients = clientRepository.findAll();

        if (clients.isEmpty()) {
            throw new ClientNotFoundException("Nenhum cliente encontrado.");
        }

        logger.info("Encontrados {} clientes", clients.size());
        return clients.stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ClientResponse findById(UUID id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("Client not found"));

        logger.info("Cliente encontrado: {}", client);
        return clientMapper.toDTO(client);
    }

    public String uploadFile(MultipartFile file, UUID clientId, String docType) throws IOException {
        validateInputs(file, clientId, docType);
        String filePath = saveFile(file, clientId, docType);
        saveClientDocument(file, clientId, docType, filePath);
        return filePath;
    }

    private void saveClientDocument(MultipartFile file, UUID clientId, String docType, String filePath) {
        ClientDocument clientDocument = new ClientDocument();

        ClientDocumentType clientDocumentType = ClientDocumentType.findByName(docType);
        clientDocument.setType(clientDocumentType);
        clientDocument.setFileName(file.getOriginalFilename());
        clientDocument.setFileUrl(filePath);

        Optional<Client> clientResponse = clientRepository.findById(clientId);
        if (clientResponse.isEmpty()) {
            throw new ClientNotFoundException("Cliente não encontrado");
        }
        clientDocument.setClient(clientResponse.get());

        documentRepository.save(clientDocument);
    }

    private static String saveFile(MultipartFile file, UUID clientId, String docType) throws IOException {
        String directory = createDirectory(clientId);
        String filePath = directory + docType + "-" + file.getOriginalFilename();
        file.transferTo(new File(filePath));
        return filePath;
    }

    private static String createDirectory(UUID clientId) throws IOException {
        String directory = UPLOAD_DIRECTORY + SLASH + clientId + SLASH;
        Files.createDirectories(Paths.get(directory));
        return directory;
    }

    private void validateInputs(MultipartFile file, UUID clientId, String docType) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo inválido");
        }
        if (clientId == null) {
            throw new IllegalArgumentException("ID do cliente não pode ser nulo");
        }
        if (docType == null || docType.isBlank()) {
            throw new IllegalArgumentException("Tipo de documento não pode ser vazio");
        }
    }
}


