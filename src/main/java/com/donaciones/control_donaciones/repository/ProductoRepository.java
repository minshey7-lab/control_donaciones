package com.donaciones.control_donaciones.repository;

import com.donaciones.control_donaciones.entity.ProductoDonado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoDonado, Long> {
}