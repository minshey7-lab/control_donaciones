package com.donaciones.control_donaciones.dto;

public class ResumenIndicadoresDTO {
    private Long totalUnidadesRecibidas;
    private Long totalUnidadesEntregadas;

    public ResumenIndicadoresDTO(Long totalUnidadesRecibidas, Long totalUnidadesEntregadas) {
        this.totalUnidadesRecibidas = totalUnidadesRecibidas != null ? totalUnidadesRecibidas : 0L;
        this.totalUnidadesEntregadas = totalUnidadesEntregadas != null ? totalUnidadesEntregadas : 0L;
    }

    public Long getTotalUnidadesRecibidas() { return totalUnidadesRecibidas; }
    public Long getTotalUnidadesEntregadas() { return totalUnidadesEntregadas; }
}