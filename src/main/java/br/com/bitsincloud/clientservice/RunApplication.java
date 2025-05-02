package br.com.bitsincloud.clientservice;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class RunApplication {

    // Injeta a variável que deveria vir do config-server
    @Value("${spring.datasource.url:NOT_DEFINED}")
    private String datasourceUrl;

    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class, args);
    }

    @PostConstruct
    public void printDatasourceUrl() {
        log.info("\uD83D\uDD0D spring.datasource.url = {}", datasourceUrl);
    }

}
