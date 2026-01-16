package com.devsu.hackerearth.backend.client.infrastructure.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequest {
    private PersonaRequest persona;
    private String contrasena;
    private Boolean estado;
}
