package com.control.shift.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ObraSocial.
 */
@Entity
@Table(name = "obra_social")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ObraSocial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties("obraSocials")
    private Dentista dentista;

    @OneToMany(mappedBy = "obraSocial")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Paciente> pacientes = new HashSet<>();

    @OneToMany(mappedBy = "obraSocial")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tratamiento> tratamientos = new HashSet<>();

    @OneToMany(mappedBy = "obraSocial")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Planilla> planillas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ObraSocial name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public ObraSocial description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public ObraSocial dentista(Dentista dentista) {
        this.dentista = dentista;
        return this;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    public Set<Paciente> getPacientes() {
        return pacientes;
    }

    public ObraSocial pacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
        return this;
    }

    public ObraSocial addPacientes(Paciente paciente) {
        this.pacientes.add(paciente);
        paciente.setObraSocial(this);
        return this;
    }

    public ObraSocial removePacientes(Paciente paciente) {
        this.pacientes.remove(paciente);
        paciente.setObraSocial(null);
        return this;
    }

    public void setPacientes(Set<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public Set<Tratamiento> getTratamientos() {
        return tratamientos;
    }

    public ObraSocial tratamientos(Set<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
        return this;
    }

    public ObraSocial addTratamientos(Tratamiento tratamiento) {
        this.tratamientos.add(tratamiento);
        tratamiento.setObraSocial(this);
        return this;
    }

    public ObraSocial removeTratamientos(Tratamiento tratamiento) {
        this.tratamientos.remove(tratamiento);
        tratamiento.setObraSocial(null);
        return this;
    }

    public void setTratamientos(Set<Tratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public Set<Planilla> getPlanillas() {
        return planillas;
    }

    public ObraSocial planillas(Set<Planilla> planillas) {
        this.planillas = planillas;
        return this;
    }

    public ObraSocial addPlanillas(Planilla planilla) {
        this.planillas.add(planilla);
        planilla.setObraSocial(this);
        return this;
    }

    public ObraSocial removePlanillas(Planilla planilla) {
        this.planillas.remove(planilla);
        planilla.setObraSocial(null);
        return this;
    }

    public void setPlanillas(Set<Planilla> planillas) {
        this.planillas = planillas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObraSocial)) {
            return false;
        }
        return id != null && id.equals(((ObraSocial) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ObraSocial{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
