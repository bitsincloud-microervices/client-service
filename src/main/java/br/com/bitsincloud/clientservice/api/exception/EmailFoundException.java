package br.com.bitsincloud.clientservice.api.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND, reason = "Cliente e-mail não encontrado.")
public class EmailFoundException extends RuntimeException {
    public EmailFoundException(String email) {
        super("Cliente com e-mail " + email + " não encontrado.");
    }
}
