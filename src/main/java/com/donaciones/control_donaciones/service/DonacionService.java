
package com.donaciones.control_donaciones.service;

import com.donaciones.control_donaciones.entity.Donacion;
import com.donaciones.control_donaciones.entity.Donante;
import com.donaciones.control_donaciones.repository.DonacionRepository;
import com.donaciones.control_donaciones.repository.DonanteRepository;
import com.donaciones.control_donaciones.exception.RecursoNoEncontradoException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DonacionService {

    @Autowired
    private DonacionRepository donacionRepository;

    @Autowired
    private DonanteRepository donanteRepository;



    public List<Donacion> listarDonaciones() {
        return donacionRepository.findAll();
    }



    public Optional<Donacion> obtenerPorId(Long id) {
        return donacionRepository.findById(id);
    }




    @Transactional
    public Donacion guardarDonacion(Donacion donacion) {

        validarDonacion(donacion);

        donacion.setId(null);
        prepararDatos(donacion);

        return donacionRepository.save(donacion);
    }




    @Transactional
    public Donacion actualizarDonacion(Long id, Donacion datos) {

        validarDonacion(datos);


        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Donación no encontrada"
                ));


        donacion.setDescripcionAlimento(
                datos.getDescripcionAlimento()
        );

        donacion.setCantidad(datos.getCantidad());

        donacion.setUnidadMedida(
                datos.getUnidadMedida()
        );

        donacion.setFechaDonacion(
                datos.getFechaDonacion()
        );

        donacion.setFechaCaducidad(
                datos.getFechaCaducidad()
        );

        donacion.setEstado(datos.getEstado());
        donacion.setDonante(datos.getDonante());

        prepararDatos(donacion);

        return donacionRepository.save(donacion);
    }






    @Transactional
    public void eliminarDonacion(Long id) {

        Donacion donacion = donacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Donación no encontrada"
                ));

        donacionRepository.delete(donacion);
    }




    private void validarDonacion(Donacion donacion) {

        if (donacion == null) {
            throw new IllegalArgumentException(
                    "Debe ingresar los datos de la donación"
            );
        }

        if (donacion.getDescripcionAlimento() == null ||
                donacion.getDescripcionAlimento().isBlank()) {
            throw new IllegalArgumentException(
                    "La descripción del alimento es obligatoria"
            );
        }

        if (donacion.getDescripcionAlimento().trim().length() > 150) {
            throw new IllegalArgumentException(
                    "La descripción no puede superar 150 caracteres"
            );
        }

        if (donacion.getCantidad() == null ||
                !Double.isFinite(donacion.getCantidad()) ||
                donacion.getCantidad() <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero"
            );
        }

        if (donacion.getUnidadMedida() == null ||
                donacion.getUnidadMedida().isBlank()) {
            throw new IllegalArgumentException(
                    "La unidad de medida es obligatoria"
            );
        }

        if (donacion.getUnidadMedida().trim().length() > 20) {
            throw new IllegalArgumentException(
                    "La unidad no puede superar 20 caracteres"
            );
        }

        if (donacion.getFechaDonacion() == null) {
            throw new IllegalArgumentException(
                    "La fecha de donación es obligatoria"
            );
        }

        if (donacion.getFechaCaducidad() == null) {
            throw new IllegalArgumentException(
                    "La fecha de caducidad es obligatoria"
            );
        }

        if (donacion.getFechaCaducidad()
                .isBefore(donacion.getFechaDonacion())) {
            throw new IllegalArgumentException(
                    "La fecha de caducidad no puede ser anterior a la donación"
            );
        }

        if (donacion.getEstado() == null ||
                donacion.getEstado().isBlank()) {
            throw new IllegalArgumentException(
                    "El estado de la donación es obligatorio"
            );
        }

        String estado = donacion.getEstado()
                .trim().toUpperCase();

        if (!estado.equals("RECIBIDA") &&
                !estado.equals("PENDIENTE")) {
            throw new IllegalArgumentException(
                    "El estado debe ser RECIBIDA o PENDIENTE"
            );
        }

        if (donacion.getDonante() == null ||
                donacion.getDonante().getId() == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar un donante"
            );
        }

        Long donanteId = donacion.getDonante().getId();

        if (!donanteRepository.existsById(donanteId)) {
            throw new IllegalArgumentException(
                    "El donante seleccionado no existe"
            );
        }
    }



    private void prepararDatos(Donacion donacion) {

        donacion.setDescripcionAlimento(
                donacion.getDescripcionAlimento().trim()
        );

        donacion.setUnidadMedida(
                donacion.getUnidadMedida().trim().toUpperCase()
        );

        donacion.setEstado(
                donacion.getEstado().trim().toUpperCase()
        );

        Long donanteId = donacion.getDonante().getId();

        Donante donante = donanteRepository.findById(donanteId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "El donante seleccionado no existe"
                ));

        donacion.setDonante(donante);
    }
}
