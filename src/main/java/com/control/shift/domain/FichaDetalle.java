package com.control.shift.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A FichaDetalle.
 */
@Entity
@Table(name = "ficha_detalle")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FichaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("fichaDetalles")
    private Ficha ficha;

    @OneToOne
    @JoinColumn(unique = true)
    private Tratamiento tratamiento;

    @OneToOne
    @JoinColumn(unique = true)
    private Diente diente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ficha getFicha() {
        return ficha;
    }

    public FichaDetalle ficha(Ficha ficha) {
        this.ficha = ficha;
        return this;
    }

    public void setFicha(Ficha ficha) {
        this.ficha = ficha;
    }

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public FichaDetalle tratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
        return this;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public Diente getDiente() {
        return diente;
    }

    public FichaDetalle diente(Diente diente) {
        this.diente = diente;
        return this;
    }

    public void setDiente(Diente diente) {
        this.diente = diente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FichaDetalle)) {
            return false;
        }
        return id != null && id.equals(((FichaDetalle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FichaDetalle{" +
            "id=" + getId() +
            "}";
    }
}
