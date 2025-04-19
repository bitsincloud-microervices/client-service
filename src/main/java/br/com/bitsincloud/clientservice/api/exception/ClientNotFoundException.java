package br.com.bitsincloud.clientservice.api.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND, reason = "Cliente não encontrado.")
public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(String id) {
        super("Cliente com ID " + id + " não encontrado.");
    }
}