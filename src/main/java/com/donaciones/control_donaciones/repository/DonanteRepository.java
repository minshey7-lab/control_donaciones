package com.donaciones.controldonaciones.repository;

import com.donaciones.controldonaciones.entity.Donante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonanteRepository extends JpaRepository<Donante, Long> {
}