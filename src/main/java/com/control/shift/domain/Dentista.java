package com.control.shift.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Dentista.
 */
@Entity
@Table(name = "dentista")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Dentista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "matricula")
    private Long matricula;

    @OneToMany(mappedBy = "dentista")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ObraSocial> obrasSociales = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Dentista firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Dentista lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getMatricula() {
        return matricula;
    }

    public Dentista matricula(Long matricula) {
        this.matricula = matricula;
        return this;
    }

    public void setMatricula(Long matricula) {
        this.matricula = matricula;
    }

    public Set<ObraSocial> getObrasSociales() {
        return obrasSociales;
    }

    public Dentista obrasSociales(Set<ObraSocial> obraSocials) {
        this.obrasSociales = obraSocials;
        return this;
    }

    public Dentista addObrasSociales(ObraSocial obraSocial) {
        this.obrasSociales.add(obraSocial);
        obraSocial.setDentista(this);
        return this;
    }

    public Dentista removeObrasSociales(ObraSocial obraSocial) {
        this.obrasSociales.remove(obraSocial);
        obraSocial.setDentista(null);
        return this;
    }

    public void setObrasSociales(Set<ObraSocial> obraSocials) {
        this.obrasSociales = obraSocials;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dentista)) {
            return false;
        }
        return id != null && id.equals(((Dentista) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dentista{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", matricula=" + getMatricula() +
            "}";
    }
}
