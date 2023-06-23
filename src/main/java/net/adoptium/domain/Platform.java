package com.cyberpoint.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Platform.
 */
@Entity
@Table(name = "platform")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Platform implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Min(value = 1L)
    @Max(value = 10L)
    @Column(name = "access_level", nullable = false)
    private Long accessLevel;

    @Column(name = "version")
    private String version;

    @Lob
    @Column(name = "contents")
    private byte[] contents;

    @Column(name = "contents_content_type")
    private String contentsContentType;

    @Column(name = "added")
    private Instant added;

    @Column(name = "updated")
    private Instant updated;

    @Column(name = "active")
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Platform id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Platform name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public Platform description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getAccessLevel() {
        return this.accessLevel;
    }

    public Platform accessLevel(Long accessLevel) {
        this.setAccessLevel(accessLevel);
        return this;
    }

    public void setAccessLevel(Long accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getVersion() {
        return this.version;
    }

    public Platform version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public byte[] getContents() {
        return this.contents;
    }

    public Platform contents(byte[] contents) {
        this.setContents(contents);
        return this;
    }

    public void setContents(byte[] contents) {
        this.contents = contents;
    }

    public String getContentsContentType() {
        return this.contentsContentType;
    }

    public Platform contentsContentType(String contentsContentType) {
        this.contentsContentType = contentsContentType;
        return this;
    }

    public void setContentsContentType(String contentsContentType) {
        this.contentsContentType = contentsContentType;
    }

    public Instant getAdded() {
        return this.added;
    }

    public Platform added(Instant added) {
        this.setAdded(added);
        return this;
    }

    public void setAdded(Instant added) {
        this.added = added;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public Platform updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Boolean getActive() {
        return this.active;
    }

    public Platform active(Boolean active) {
        this.setActive(active);
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Platform)) {
            return false;
        }
        return id != null && id.equals(((Platform) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Platform{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", accessLevel=" + getAccessLevel() +
            ", version='" + getVersion() + "'" +
            ", contents='" + getContents() + "'" +
            ", contentsContentType='" + getContentsContentType() + "'" +
            ", added='" + getAdded() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", active='" + getActive() + "'" +
            "}";
    }
}
