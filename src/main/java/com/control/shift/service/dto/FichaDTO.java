package com.control.shift.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.control.shift.domain.Ficha} entity.
 */
public class FichaDTO implements Serializable {

    private Long id;

    private String month;

    private Boolean urgency;


    private Long pacienteId;

    private Long planillaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Boolean isUrgency() {
        return urgency;
    }

    public void setUrgency(Boolean urgency) {
        this.urgency = urgency;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getPlanillaId() {
        return planillaId;
    }

    public void setPlanillaId(Long planillaId) {
        this.planillaId = planillaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FichaDTO fichaDTO = (FichaDTO) o;
        if (fichaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fichaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FichaDTO{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", urgency='" + isUrgency() + "'" +
            ", paciente=" + getPacienteId() +
            ", planilla=" + getPlanillaId() +
            "}";
    }
}
