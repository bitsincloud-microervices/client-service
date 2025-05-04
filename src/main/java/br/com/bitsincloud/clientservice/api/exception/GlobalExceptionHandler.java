package br.com.bitsincloud.clientservice.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleClientNotFound(ClientNotFoundException ex, HttpServletRequest request) {
        return buildProblemDetail(
                ex,
                HttpStatus.NOT_FOUND,
                request.getRequestURI(),
                "https://errors.bitsincloud.com/client/not-found"
        );
    }

    @ExceptionHandler(NoClientsFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoClientsFound(NoClientsFoundException ex, HttpServletRequest request) {
        return buildProblemDetail(
                ex,
                HttpStatus.NOT_FOUND,
                request.getRequestURI(),
                "https://errors.bitsincloud.com/client/no-clients-found"
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return buildProblemDetail(
                new Exception(message),
                HttpStatus.BAD_REQUEST,
                request.getRequestURI(),
                "https://errors.bitsincloud.com/client/validation-error"
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest request) {
        String message = "Violação de integridade de dados";
        String type = "https://errors.bitsincloud.com/client/integrity-violation";

        if (ex.getCause() instanceof ConstraintViolationException cve) {
            String constraint = cve.getConstraintName();
            if (constraint != null && constraint.contains("tb_client_name_key")) {
                String duplicatedName = extractValue(cve.getSQLException().getMessage());

                Locale locale = request.getLocale();
                message = messageSource.getMessage(
                        "client.name.duplicate",
                        new Object[]{duplicatedName},
                        locale
                );

                type = "https://errors.bitsincloud.com/client/duplicated-name";
            }
        }

        return buildProblemDetail(
                new Exception(message),
                HttpStatus.CONFLICT,
                request.getRequestURI(),
                type
        );
    }

    // Exceção genérica
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGeneric(Exception ex, HttpServletRequest request) {
        return buildProblemDetail(
                ex,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request.getRequestURI(),
                "https://errors.bitsincloud.com/unexpected-error"
        );
    }

    private ResponseEntity<ProblemDetail> buildProblemDetail(Exception ex, HttpStatus status, String path, String type) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                status,
                ex.getMessage()
        );
        problem.setTitle(status.getReasonPhrase());
        problem.setInstance(URI.create(path));
        problem.setType(URI.create(type));

        logger.error("Erro capturado: {} - {}", type, ex.getMessage());

        return ResponseEntity.status(status).body(problem);
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
