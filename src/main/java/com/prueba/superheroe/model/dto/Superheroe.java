package com.prueba.superheroe.model.dto;

import lombok.Data;

@Data
public class Superheroe {

    private Long id;

    private String nombre;
    private String habilidad;
    private String universo;

    public Superheroe() {
        super();
    }

}
