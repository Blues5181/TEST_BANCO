package com.devsu.hackerearth.backend.client.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.client.domain.model.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona,Long> {
  Optional<Persona> findByIdentificacion(String identificacion);  
}
