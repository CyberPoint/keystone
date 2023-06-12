package com.cyberpoint.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Agent.
 */
@Entity
@Table(name = "agent")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Agent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "classification")
    private String classification;

    @Column(name = "description")
    private String description;

    @Column(name = "installed_on")
    private Instant installedOn;

    @Column(name = "uninstall_date")
    private Instant uninstallDate;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "deactivate")
    private Boolean deactivate;

    @Column(name = "auto_registered")
    private Boolean autoRegistered;

    @Column(name = "approved")
    private Boolean approved;

    @ManyToOne(fetch = FetchType.LAZY)
    private Platform platform;

    @JsonIgnoreProperties(value = { "agent", "secret" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "agent")
    private RegistrationEvent registrationEvent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Agent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Agent name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassification() {
        return this.classification;
    }

    public Agent classification(String classification) {
        this.setClassification(classification);
        return this;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getDescription() {
        return this.description;
    }

    public Agent description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getInstalledOn() {
        return this.installedOn;
    }

    public Agent installedOn(Instant installedOn) {
        this.setInstalledOn(installedOn);
        return this;
    }

    public void setInstalledOn(Instant installedOn) {
        this.installedOn = installedOn;
    }

    public Instant getUninstallDate() {
        return this.uninstallDate;
    }

    public Agent uninstallDate(Instant uninstallDate) {
        this.setUninstallDate(uninstallDate);
        return this;
    }

    public void setUninstallDate(Instant uninstallDate) {
        this.uninstallDate = uninstallDate;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Agent active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDeactivate() {
        return this.deactivate;
    }

    public Agent deactivate(Boolean deactivate) {
        this.setDeactivate(deactivate);
        return this;
    }

    public void setDeactivate(Boolean deactivate) {
        this.deactivate = deactivate;
    }

    public Boolean getAutoRegistered() {
        return this.autoRegistered;
    }

    public Agent autoRegistered(Boolean autoRegistered) {
        this.setAutoRegistered(autoRegistered);
        return this;
    }

    public void setAutoRegistered(Boolean autoRegistered) {
        this.autoRegistered = autoRegistered;
    }

    public Boolean getApproved() {
        return this.approved;
    }

    public Agent approved(Boolean approved) {
        this.setApproved(approved);
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Platform getPlatform() {
        return this.platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Agent platform(Platform platform) {
        this.setPlatform(platform);
        return this;
    }

    public RegistrationEvent getRegistrationEvent() {
        return this.registrationEvent;
    }

    public void setRegistrationEvent(RegistrationEvent registrationEvent) {
        if (this.registrationEvent != null) {
            this.registrationEvent.setAgent(null);
        }
        if (registrationEvent != null) {
            registrationEvent.setAgent(this);
        }
        this.registrationEvent = registrationEvent;
    }

    public Agent registrationEvent(RegistrationEvent registrationEvent) {
        this.setRegistrationEvent(registrationEvent);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Agent)) {
            return false;
        }
        return id != null && id.equals(((Agent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Agent{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", classification='" + getClassification() + "'" +
            ", description='" + getDescription() + "'" +
            ", installedOn='" + getInstalledOn() + "'" +
            ", uninstallDate='" + getUninstallDate() + "'" +
            ", active='" + getActive() + "'" +
            ", deactivate='" + getDeactivate() + "'" +
            ", autoRegistered='" + getAutoRegistered() + "'" +
            ", approved='" + getApproved() + "'" +
            "}";
    }
}
