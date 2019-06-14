package com.control.shift.service.dto;
import java.io.Serializable;
import java.util.Objects;
import com.control.shift.domain.enumeration.Cara;

/**
 * A DTO for the {@link com.control.shift.domain.Diente} entity.
 */
public class DienteDTO implements Serializable {

    private Long id;

    private Cara cara;

    private Integer numero;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cara getCara() {
        return cara;
    }

    public void setCara(Cara cara) {
        this.cara = cara;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DienteDTO dienteDTO = (DienteDTO) o;
        if (dienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DienteDTO{" +
            "id=" + getId() +
            ", cara='" + getCara() + "'" +
            ", numero=" + getNumero() +
            "}";
    }
}
