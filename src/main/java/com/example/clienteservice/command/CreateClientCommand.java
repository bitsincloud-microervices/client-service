package com.example.clienteservice.command;

import lombok.Data;

import java.io.Serializable;

@Data
public class CreateClientCommand implements Serializable {
    private String name;
    private String email;
}
