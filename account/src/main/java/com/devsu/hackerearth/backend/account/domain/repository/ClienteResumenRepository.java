package com.devsu.hackerearth.backend.account.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsu.hackerearth.backend.account.domain.model.ClienteResumen;

@Repository
public interface ClienteResumenRepository extends JpaRepository<ClienteResumen,Long>
{
  Optional<ClienteResumen> findByClienteId(Long clienteId);

}