package com.donaciones.control_donaciones.service;

import com.donaciones.control_donaciones.entity.Donante;
import com.donaciones.control_donaciones.repository.DonanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonanteService {

    @Autowired
    private DonanteRepository donanteRepository;

    public List<Donante> listarDonantes() {
        return donanteRepository.findAll();
    }

    public Donante guardarDonante(Donante donante) {
        return donanteRepository.save(donante);
    }

    public Optional<Donante> obtenerPorId(Long id) {
        return donanteRepository.findById(id);
    }

    public void eliminarDonante(Long id) {
        donanteRepository.deleteById(id);
    }
}