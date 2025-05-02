package br.com.bitsincloud.clientservice.api.controller;

import br.com.bitsincloud.clientservice.domain.service.ToogleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/toogle")
public class ToogleController {

    private final ToogleService toogleService;

    public ToogleController(ToogleService toogleService) {
        this.toogleService = toogleService;
    }

    @GetMapping
    public boolean getPararProcesso() {
        return toogleService.isPararProcesso();
    }
}
