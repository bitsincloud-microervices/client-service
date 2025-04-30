package br.com.bitsincloud.clientservice;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        System.out.println("🔍 spring.datasource.url = " + datasourceUrl);
    }
}
