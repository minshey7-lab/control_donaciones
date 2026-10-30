package com.donaciones.control_donaciones.service;

import com.donaciones.control_donaciones.entity.Donacion;
import com.donaciones.control_donaciones.repository.DonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonacionService {

    @Autowired
    private DonacionRepository donacionRepository;

    public List<Donacion> listarDonaciones() {
        return donacionRepository.findAll();
    }

    public Donacion guardarDonacion(Donacion donacion) {
        return donacionRepository.save(donacion);
    }

    public Optional<Donacion> obtenerPorId(Long id) {
        return donacionRepository.findById(id);
    }

    public void eliminarDonacion(Long id) {
        donacionRepository.deleteById(id);
    }
}