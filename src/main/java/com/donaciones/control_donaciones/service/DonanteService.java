
package com.donaciones.control_donaciones.service;

import com.donaciones.control_donaciones.entity.Donante;
import com.donaciones.control_donaciones.repository.DonanteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.donaciones.control_donaciones.exception.RecursoNoEncontradoException;

import java.util.List;
import java.util.Optional;

@Service
public class DonanteService {

    @Autowired
    private DonanteRepository donanteRepository;

    // Consultar donantes
    public List<Donante> listarDonantes() {
        return donanteRepository.findAll();
    }

    // Buscar donante por ID
    public Optional<Donante> obtenerPorId(Long id) {
        return donanteRepository.findById(id);
    }

    // Registrar donante
    @Transactional
    public Donante guardarDonante(Donante donante) {

        validarDonante(donante);

        donante.setId(null);
        prepararDatos(donante);

        return donanteRepository.save(donante);
    }

    // Modificar donante
    @Transactional
    public Donante actualizarDonante(Long id, Donante datos) {

        validarDonante(datos);

        Donante donante = donanteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Donante no encontrado"
                ));



        donante.setNombre(datos.getNombre());
        donante.setTelefono(datos.getTelefono());
        donante.setTipo(datos.getTipo());
        donante.setDireccion(datos.getDireccion());

        prepararDatos(donante);

        return donanteRepository.save(donante);
    }

    // Eliminar donante
    @Transactional
    public void eliminarDonante(Long id) {

        Donante donante = donanteRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Donante no encontrado"));

        if (donante.getDonaciones() != null &&
                !donante.getDonaciones().isEmpty()) {

            throw new IllegalArgumentException(
                    "No se puede eliminar un donante que tiene donaciones registradas");
        }

        donanteRepository.delete(donante);
    }

    // Validar información
    private void validarDonante(Donante donante) {

        if (donante == null) {
            throw new IllegalArgumentException(
                    "Debe ingresar los datos del donante");
        }

        if (donante.getNombre() == null ||
                donante.getNombre().isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre del donante es obligatorio");
        }

        if (donante.getNombre().trim().length() > 100) {
            throw new IllegalArgumentException(
                    "El nombre no puede superar 100 caracteres");
        }

        if (donante.getTelefono() == null ||
                donante.getTelefono().isBlank()) {
            throw new IllegalArgumentException(
                    "El teléfono es obligatorio");
        }

        String telefono = donante.getTelefono().trim();

        if (telefono.length() > 20 ||
                !telefono.matches("[0-9+() -]+")) {
            throw new IllegalArgumentException(
                    "El teléfono contiene caracteres no válidos");
        }

        if (donante.getTipo() == null ||
                donante.getTipo().isBlank()) {
            throw new IllegalArgumentException(
                    "El tipo de donante es obligatorio");
        }

        String tipo = donante.getTipo().trim().toUpperCase();

        if (!tipo.equals("PERSONA") &&
                !tipo.equals("ORGANIZACION")) {
            throw new IllegalArgumentException(
                    "El tipo debe ser PERSONA u ORGANIZACION");
        }

        if (donante.getDireccion() == null ||
                donante.getDireccion().isBlank()) {
            throw new IllegalArgumentException(
                    "La dirección del donante es obligatoria");
        }

        if (donante.getDireccion().trim().length() > 150) {
            throw new IllegalArgumentException(
                    "La dirección no puede superar 150 caracteres");
        }
    }

    // Preparar datos
    private void prepararDatos(Donante donante) {

        donante.setNombre(donante.getNombre().trim());
        donante.setTelefono(donante.getTelefono().trim());
        donante.setTipo(donante.getTipo().trim().toUpperCase());
        donante.setDireccion(donante.getDireccion().trim());
    }
}
