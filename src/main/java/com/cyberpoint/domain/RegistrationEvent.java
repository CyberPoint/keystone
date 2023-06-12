package com.cyberpoint.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RegistrationEvent.
 */
@Entity
@Table(name = "registration_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RegistrationEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "raw_contents")
    private String rawContents;

    @Column(name = "remote_port")
    private Integer remotePort;

    @Column(name = "name")
    private String name;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "registration_date")
    private Instant registrationDate;

    @JsonIgnoreProperties(value = { "platform", "registrationEvent" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private Agent agent;

    @JsonIgnoreProperties(value = { "registrationEvent" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(unique = true)
    private RegistrationSecret secret;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RegistrationEvent id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public RegistrationEvent ipAddress(String ipAddress) {
        this.setIpAddress(ipAddress);
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getRawContents() {
        return this.rawContents;
    }

    public RegistrationEvent rawContents(String rawContents) {
        this.setRawContents(rawContents);
        return this;
    }

    public void setRawContents(String rawContents) {
        this.rawContents = rawContents;
    }

    public Integer getRemotePort() {
        return this.remotePort;
    }

    public RegistrationEvent remotePort(Integer remotePort) {
        this.setRemotePort(remotePort);
        return this;
    }

    public void setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
    }

    public String getName() {
        return this.name;
    }

    public RegistrationEvent name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getApproved() {
        return this.approved;
    }

    public RegistrationEvent approved(Boolean approved) {
        this.setApproved(approved);
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Instant getRegistrationDate() {
        return this.registrationDate;
    }

    public RegistrationEvent registrationDate(Instant registrationDate) {
        this.setRegistrationDate(registrationDate);
        return this;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Agent getAgent() {
        return this.agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public RegistrationEvent agent(Agent agent) {
        this.setAgent(agent);
        return this;
    }

    public RegistrationSecret getSecret() {
        return this.secret;
    }

    public void setSecret(RegistrationSecret registrationSecret) {
        this.secret = registrationSecret;
    }

    public RegistrationEvent secret(RegistrationSecret registrationSecret) {
        this.setSecret(registrationSecret);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RegistrationEvent)) {
            return false;
        }
        return id != null && id.equals(((RegistrationEvent) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RegistrationEvent{" +
            "id=" + getId() +
            ", ipAddress='" + getIpAddress() + "'" +
            ", rawContents='" + getRawContents() + "'" +
            ", remotePort=" + getRemotePort() +
            ", name='" + getName() + "'" +
            ", approved='" + getApproved() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            "}";
    }
}
