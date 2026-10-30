package com.donaciones.control_donaciones.repository;

import com.donaciones.control_donaciones.entity.MovimientoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoRepository extends JpaRepository<MovimientoAlimento, Long> {
}