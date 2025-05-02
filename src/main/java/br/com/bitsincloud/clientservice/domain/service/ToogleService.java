package br.com.bitsincloud.clientservice.domain.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RefreshScope // O @RefreshScope faz com que o Spring recrie a instância da classe quando o bus-refresh é acionado,
            // atualizando todos os @Value e as configurações.
public class ToogleService {

    @Value("${client.toggle-process:true}")
    private boolean pararProcesso;

    @PostConstruct
    public void init() {
        log.info("Valor atual de pararProcesso: {}", pararProcesso);
    }

    public boolean isPararProcesso() {
        return pararProcesso;
    }
}
