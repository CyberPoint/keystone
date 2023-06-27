package com.cyberpoint.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TaskResult.
 */
@Entity
@Table(name = "task_result")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TaskResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * The firstname attribute.
     */
    @Schema(description = "The firstname attribute.")
    @Lob
    @Column(name = "embeddeddata")
    private byte[] embeddeddata;

    @Column(name = "embeddeddata_content_type")
    private String embeddeddataContentType;

    @Column(name = "added")
    private Instant added;

    @Column(name = "reviewed")
    private Boolean reviewed;

    @NotNull
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "headers")
    private String headers;

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "results" }, allowSetters = true)
    private Task task;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TaskResult id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getEmbeddeddata() {
        return this.embeddeddata;
    }

    public TaskResult embeddeddata(byte[] embeddeddata) {
        this.setEmbeddeddata(embeddeddata);
        return this;
    }

    public void setEmbeddeddata(byte[] embeddeddata) {
        this.embeddeddata = embeddeddata;
    }

    public String getEmbeddeddataContentType() {
        return this.embeddeddataContentType;
    }

    public TaskResult embeddeddataContentType(String embeddeddataContentType) {
        this.embeddeddataContentType = embeddeddataContentType;
        return this;
    }

    public void setEmbeddeddataContentType(String embeddeddataContentType) {
        this.embeddeddataContentType = embeddeddataContentType;
    }

    public Instant getAdded() {
        return this.added;
    }

    public TaskResult added(Instant added) {
        this.setAdded(added);
        return this;
    }

    public void setAdded(Instant added) {
        this.added = added;
    }

    public Boolean getReviewed() {
        return this.reviewed;
    }

    public TaskResult reviewed(Boolean reviewed) {
        this.setReviewed(reviewed);
        return this;
    }

    public void setReviewed(Boolean reviewed) {
        this.reviewed = reviewed;
    }

    public String getIpAddress() {
        return this.ipAddress;
    }

    public TaskResult ipAddress(String ipAddress) {
        this.setIpAddress(ipAddress);
        return this;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHeaders() {
        return this.headers;
    }

    public TaskResult headers(String headers) {
        this.setHeaders(headers);
        return this;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getUrl() {
        return this.url;
    }

    public TaskResult url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Task getTask() {
        return this.task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskResult task(Task task) {
        this.setTask(task);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskResult)) {
            return false;
        }
        return id != null && id.equals(((TaskResult) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskResult{" +
            "id=" + getId() +
            ", embeddeddata='" + getEmbeddeddata() + "'" +
            ", embeddeddataContentType='" + getEmbeddeddataContentType() + "'" +
            ", added='" + getAdded() + "'" +
            ", reviewed='" + getReviewed() + "'" +
            ", ipAddress='" + getIpAddress() + "'" +
            ", headers='" + getHeaders() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
