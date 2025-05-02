package br.com.bitsincloud.clientservice.gateway;

import br.com.bitsincloud.clientservice.gateway.dto.AddressRequest;
import br.com.bitsincloud.clientservice.gateway.dto.AddressResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class AddressGateway {

    private final RestTemplate restTemplate;

    @Value("${address.service-url}") // exemplo: http://endereco-service/api/enderecos/resolve
    private String addressServiceUrl;

    public AddressGateway(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public UUID resolveStreetToId(String street) {
        AddressRequest request = new AddressRequest(street);
        ResponseEntity<AddressResponse> response = restTemplate.postForEntity(
                addressServiceUrl,
                request,
                AddressResponse.class
        );
        return Objects.requireNonNull(response.getBody()).id();
    }
}
