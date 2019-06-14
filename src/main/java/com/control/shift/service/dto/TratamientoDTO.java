package com.control.shift.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.control.shift.domain.Tratamiento} entity.
 */
public class TratamientoDTO implements Serializable {

    private Long id;

    @NotNull
    private Long code;

    @NotNull
    private String description;

    private BigDecimal precio;


    private Long obraSocialId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
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

        TratamientoDTO tratamientoDTO = (TratamientoDTO) o;
        if (tratamientoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tratamientoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TratamientoDTO{" +
            "id=" + getId() +
            ", code=" + getCode() +
            ", description='" + getDescription() + "'" +
            ", precio=" + getPrecio() +
            ", obraSocial=" + getObraSocialId() +
            "}";
    }
}
