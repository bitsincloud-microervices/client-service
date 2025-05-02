package br.com.bitsincloud.clientservice.util;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidateUtils {

    public static boolean validateCpf(String cpf) {
        CPFValidator validator = new CPFValidator();
        try {
            validator.assertValid(cpf);
            return true;
        } catch (InvalidStateException e) {
            log.info("Invalid CPF: {}", e.getMessage());
            return false;
        }
    }
}
