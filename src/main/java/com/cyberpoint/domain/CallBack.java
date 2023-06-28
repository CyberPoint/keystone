package com.cyberpoint.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CallBack.
 */
@Entity
@Table(name = "call_back")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CallBack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "url")
    private String url;

    @Column(name = "remote_port")
    private Integer remotePort;

    @Column(name = "timestamp")
    private Instant timestamp;

    @Column(name = "buffer")
    private String buffer;

    @Lob
    @Column(name = "rawcontents")
    private byte[] rawcontents;

    @Column(name = "rawcontents_content_type")
    private String rawcontentsContentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tasks", "platform", "registrationEvent" }, allowSetters = true)
    private Agent agent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CallBack id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public CallBack ipAddress(String ipAddress) {
        this.setIpAddress(ipAddress);
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getUrl() {
        return this.url;
    }

    public CallBack url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRemotePort() {
        return this.remotePort;
    }

    public CallBack remotePort(Integer remotePort) {
        this.setRemotePort(remotePort);
        return this;
    }

    public void setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public CallBack timestamp(Instant timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getBuffer() {
        return this.buffer;
    }

    public CallBack buffer(String buffer) {
        this.setBuffer(buffer);
        return this;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public byte[] getRawcontents() {
        return this.rawcontents;
    }

    public CallBack rawcontents(byte[] rawcontents) {
        this.setRawcontents(rawcontents);
        return this;
    }

    public void setRawcontents(byte[] rawcontents) {
        this.rawcontents = rawcontents;
    }

    public String getRawcontentsContentType() {
        return this.rawcontentsContentType;
    }

    public CallBack rawcontentsContentType(String rawcontentsContentType) {
        this.rawcontentsContentType = rawcontentsContentType;
        return this;
    }

    public void setRawcontentsContentType(String rawcontentsContentType) {
        this.rawcontentsContentType = rawcontentsContentType;
    }

    public Agent getAgent() {
        return this.agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public CallBack agent(Agent agent) {
        this.setAgent(agent);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CallBack)) {
            return false;
        }
        return id != null && id.equals(((CallBack) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CallBack{" +
            "id=" + getId() +
            ", ipAddress='" + getIpAddress() + "'" +
            ", url='" + getUrl() + "'" +
            ", remotePort=" + getRemotePort() +
            ", timestamp='" + getTimestamp() + "'" +
            ", buffer='" + getBuffer() + "'" +
            ", rawcontents='" + getRawcontents() + "'" +
            ", rawcontentsContentType='" + getRawcontentsContentType() + "'" +
            "}";
    }
}
