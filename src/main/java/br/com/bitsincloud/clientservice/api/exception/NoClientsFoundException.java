package br.com.bitsincloud.clientservice.api.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND, reason = "Nenhum cliente encontrado.")
public class NoClientsFoundException extends RuntimeException {
    public NoClientsFoundException() {
        super("Nenhum cliente encontrado.");
    }
}
