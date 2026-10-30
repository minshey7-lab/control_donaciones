package com.donaciones.control_donaciones.repository;

import com.donaciones.control_donaciones.entity.Donante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonanteRepository extends JpaRepository<Donante, Long> {
}