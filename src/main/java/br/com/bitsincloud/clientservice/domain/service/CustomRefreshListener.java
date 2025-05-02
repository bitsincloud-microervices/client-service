package br.com.bitsincloud.clientservice.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bus.event.RefreshRemoteApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomRefreshListener {

    @EventListener(RefreshRemoteApplicationEvent.class)
    public void onRefresh(RefreshRemoteApplicationEvent event) {
        log.info("Evento de refresh recebido do Config Server! Source: " + event.getSource());
    }
}