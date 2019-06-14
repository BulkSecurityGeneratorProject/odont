package com.control.shift.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import com.control.shift.domain.enumeration.Cara;

/**
 * A Diente.
 */
@Entity
@Table(name = "diente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Diente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "cara")
    private Cara cara;

    @Column(name = "numero")
    private Integer numero;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cara getCara() {
        return cara;
    }

    public Diente cara(Cara cara) {
        this.cara = cara;
        return this;
    }

    public void setCara(Cara cara) {
        this.cara = cara;
    }

    public Integer getNumero() {
        return numero;
    }

    public Diente numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Diente)) {
            return false;
        }
        return id != null && id.equals(((Diente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Diente{" +
            "id=" + getId() +
            ", cara='" + getCara() + "'" +
            ", numero=" + getNumero() +
            "}";
    }
}
