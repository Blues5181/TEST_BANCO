package com.devsu.hackerearth.backend.client.domain.model;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 100)
    private String nombre;
    
    @Column(length = 20)
    private String genero;
    
    @Column(nullable = false)
    private Integer edad;
    
    @Column(nullable = false, unique = true, length = 20)
    private String identificacion;
    
    @Column(length = 200)
    private String direccion;
    
    @Column(length = 20)
    private String telefono;
    
    
}