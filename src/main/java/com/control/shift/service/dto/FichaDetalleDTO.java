package com.control.shift.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.control.shift.domain.FichaDetalle} entity.
 */
public class FichaDetalleDTO implements Serializable {

    private Long id;


    private Long fichaId;

    private Long tratamientoId;

    private Long dienteId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFichaId() {
        return fichaId;
    }

    public void setFichaId(Long fichaId) {
        this.fichaId = fichaId;
    }

    public Long getTratamientoId() {
        return tratamientoId;
    }

    public void setTratamientoId(Long tratamientoId) {
        this.tratamientoId = tratamientoId;
    }

    public Long getDienteId() {
        return dienteId;
    }

    public void setDienteId(Long dienteId) {
        this.dienteId = dienteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FichaDetalleDTO fichaDetalleDTO = (FichaDetalleDTO) o;
        if (fichaDetalleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fichaDetalleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FichaDetalleDTO{" +
            "id=" + getId() +
            ", ficha=" + getFichaId() +
            ", tratamiento=" + getTratamientoId() +
            ", diente=" + getDienteId() +
            "}";
    }
}
