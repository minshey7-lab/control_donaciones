package com.donaciones.control_donaciones.repository;


import com.donaciones.control_donaciones.entity.Donacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonacionRepository extends JpaRepository<Donacion, Long> {
}