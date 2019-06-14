package com.control.shift.service.dto;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.control.shift.domain.Planilla} entity.
 */
public class PlanillaDTO implements Serializable {

    private Long id;

    private LocalDate fechaDesde;

    private LocalDate fechaHasta;


    private Long obraSocialId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(LocalDate fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Long getObraSocialId() {
        return obraSocialId;
    }

    public void setObraSocialId(Long obraSocialId) {
        this.obraSocialId = obraSocialId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlanillaDTO planillaDTO = (PlanillaDTO) o;
        if (planillaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), planillaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlanillaDTO{" +
            "id=" + getId() +
            ", fechaDesde='" + getFechaDesde() + "'" +
            ", fechaHasta='" + getFechaHasta() + "'" +
            ", obraSocial=" + getObraSocialId() +
            "}";
    }
}
