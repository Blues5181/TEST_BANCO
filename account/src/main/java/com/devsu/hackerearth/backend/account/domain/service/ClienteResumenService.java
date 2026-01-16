package com.devsu.hackerearth.backend.account.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.hackerearth.backend.account.domain.model.ClienteResumen;
import com.devsu.hackerearth.backend.account.domain.repository.ClienteResumenRepository;


@Service
public class ClienteResumenService {
    private ClienteResumenRepository clienteResumenRepository;

    @Autowired
    public ClienteResumenService(ClienteResumenRepository clienteResumenRepository) {
        this.clienteResumenRepository = clienteResumenRepository;
    }

    @Transactional(readOnly=true)
    public boolean existeclienteResumen(Long id){

        return clienteResumenRepository.existsById(id);
    }

}