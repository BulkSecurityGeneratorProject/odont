package com.control.shift.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.control.shift.domain.ObraSocial} entity.
 */
public class ObraSocialDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    private String description;


    private Long dentistaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDentistaId() {
        return dentistaId;
    }

    public void setDentistaId(Long dentistaId) {
        this.dentistaId = dentistaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ObraSocialDTO obraSocialDTO = (ObraSocialDTO) o;
        if (obraSocialDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), obraSocialDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ObraSocialDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", dentista=" + getDentistaId() +
            "}";
    }
}
