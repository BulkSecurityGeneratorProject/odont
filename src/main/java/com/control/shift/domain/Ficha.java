package com.control.shift.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Ficha.
 */
@Entity
@Table(name = "ficha")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ficha implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "month")
    private String month;

    @Column(name = "urgency")
    private Boolean urgency;

    @ManyToOne
    @JsonIgnoreProperties("fichas")
    private Paciente paciente;

    @OneToMany(mappedBy = "ficha")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FichaDetalle> detalles = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("fichas")
    private Planilla planilla;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public Ficha month(String month) {
        this.month = month;
        return this;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Boolean isUrgency() {
        return urgency;
    }

    public Ficha urgency(Boolean urgency) {
        this.urgency = urgency;
        return this;
    }

    public void setUrgency(Boolean urgency) {
        this.urgency = urgency;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Ficha paciente(Paciente paciente) {
        this.paciente = paciente;
        return this;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Set<FichaDetalle> getDetalles() {
        return detalles;
    }

    public Ficha detalles(Set<FichaDetalle> fichaDetalles) {
        this.detalles = fichaDetalles;
        return this;
    }

    public Ficha addDetalles(FichaDetalle fichaDetalle) {
        this.detalles.add(fichaDetalle);
        fichaDetalle.setFicha(this);
        return this;
    }

    public Ficha removeDetalles(FichaDetalle fichaDetalle) {
        this.detalles.remove(fichaDetalle);
        fichaDetalle.setFicha(null);
        return this;
    }

    public void setDetalles(Set<FichaDetalle> fichaDetalles) {
        this.detalles = fichaDetalles;
    }

    public Planilla getPlanilla() {
        return planilla;
    }

    public Ficha planilla(Planilla planilla) {
        this.planilla = planilla;
        return this;
    }

    public void setPlanilla(Planilla planilla) {
        this.planilla = planilla;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ficha)) {
            return false;
        }
        return id != null && id.equals(((Ficha) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Ficha{" +
            "id=" + getId() +
            ", month='" + getMonth() + "'" +
            ", urgency='" + isUrgency() + "'" +
            "}";
    }
}
