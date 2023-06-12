package com.cyberpoint.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RegistrationSecret.
 */
@Entity
@Table(name = "registration_secret")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegistrationSecret implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "unique_value")
    private String uniqueValue;

    @Column(name = "numerical_value")
    private Integer numericalValue;

    @JsonIgnoreProperties(value = { "agent", "secret" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "secret")
    private RegistrationEvent registrationEvent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RegistrationSecret id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueValue() {
        return this.uniqueValue;
    }

    public RegistrationSecret uniqueValue(String uniqueValue) {
        this.setUniqueValue(uniqueValue);
        return this;
    }

    public void setUniqueValue(String uniqueValue) {
        this.uniqueValue = uniqueValue;
    }

    public Integer getNumericalValue() {
        return this.numericalValue;
    }

    public RegistrationSecret numericalValue(Integer numericalValue) {
        this.setNumericalValue(numericalValue);
        return this;
    }

    public void setNumericalValue(Integer numericalValue) {
        this.numericalValue = numericalValue;
    }

    public RegistrationEvent getRegistrationEvent() {
        return this.registrationEvent;
    }

    public void setRegistrationEvent(RegistrationEvent registrationEvent) {
        if (this.registrationEvent != null) {
            this.registrationEvent.setSecret(null);
        }
        if (registrationEvent != null) {
            registrationEvent.setSecret(this);
        }
        this.registrationEvent = registrationEvent;
    }

    public RegistrationSecret registrationEvent(RegistrationEvent registrationEvent) {
        this.setRegistrationEvent(registrationEvent);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistrationSecret)) {
            return false;
        }
        return id != null && id.equals(((RegistrationSecret) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegistrationSecret{" +
            "id=" + getId() +
            ", uniqueValue='" + getUniqueValue() + "'" +
            ", numericalValue=" + getNumericalValue() +
            "}";
    }
}
