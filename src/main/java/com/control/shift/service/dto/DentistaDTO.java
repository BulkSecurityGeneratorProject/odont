package com.control.shift.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.control.shift.domain.Dentista} entity.
 */
public class DentistaDTO implements Serializable {

    private Long id;

    private String firstName;

    private String lastName;

    private Long matricula;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getMatricula() {
        return matricula;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DentistaDTO dentistaDTO = (DentistaDTO) o;
        if (dentistaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dentistaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DentistaDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", matricula=" + getMatricula() +
            "}";
    }
}
