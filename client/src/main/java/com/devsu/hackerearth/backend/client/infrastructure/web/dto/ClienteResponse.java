package com.devsu.hackerearth.backend.client.infrastructure.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteResponse {
 private Long clienteId;
 private PersonaResponse persona;
 private Boolean estado;   
}
