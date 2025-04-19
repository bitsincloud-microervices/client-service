package br.com.bitsincloud.clientservice.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleClientNotFound(ClientNotFoundException ex, HttpServletRequest request) {
        return buildResponse(ex, HttpStatus.NOT_FOUND, request.getRequestURI());
    }

    @ExceptionHandler(NoClientsFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNoClientsFound(NoClientsFoundException ex, HttpServletRequest request) {
        return buildResponse(ex, HttpStatus.NOT_FOUND, request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return buildResponse(new Exception(message), HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    // Exceção genérica
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        String message = "Violação de integridade de dados";

        if (ex.getCause() instanceof ConstraintViolationException cve) {
            String constraint = cve.getConstraintName();
            if (constraint != null && constraint.contains("tb_client_name_key")) {
                String duplicatedName = extractValue(cve.getSQLException().getMessage());

                Locale locale = request.getLocale(); // detecta do Accept-Language
                message = messageSource.getMessage(
                        "client.name.duplicate",
                        new Object[]{duplicatedName},
                        locale
                );
            }
        }

        return buildResponse(new Exception(message), HttpStatus.CONFLICT, request.getRequestURI());
    }

    private ResponseEntity<ApiErrorResponse> buildResponse(Exception ex, HttpStatus status, String path) {
        ApiErrorResponse response = new ApiErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                path
        );
        return ResponseEntity.status(status).body(response);
    }

    private String extractValue(String raw) {
        try {
            int start = raw.indexOf("Key (name)=");
            if (start == -1) return null;
            int valueStart = raw.indexOf("(", start) + 1;
            int valueEnd = raw.indexOf(")", valueStart);
            return raw.substring(valueStart, valueEnd);
        } catch (Exception e) {
            return null;
        }
    }
}
