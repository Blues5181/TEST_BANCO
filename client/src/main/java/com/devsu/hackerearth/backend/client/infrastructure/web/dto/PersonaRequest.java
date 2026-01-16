
package com.devsu.hackerearth.backend.client.infrastructure.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaRequest {
    private String nombre;
    private String genero;
    private Integer edad;
    private String identificacion;
    private String direccion;
    private String telefono;
}
