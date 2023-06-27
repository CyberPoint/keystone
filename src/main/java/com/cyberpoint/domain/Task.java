package com.cyberpoint.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Task entity.\n@author The JHipster team.
 */
@Schema(description = "Task entity.\n@author The JHipster team.")
@Entity
@Table(name = "task")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "command")
    private String command;

    @Column(name = "implant_task_id")
    private Integer implantTaskId;

    @Column(name = "submitted_by")
    private String submittedBy;

    @Column(name = "description")
    private String description;

    @Column(name = "added")
    private Instant added;

    @Column(name = "updated")
    private Instant updated;

    @Column(name = "retrieved")
    private Boolean retrieved;

    @Column(name = "failure")
    private Boolean failure;

    @Column(name = "approved")
    private Boolean approved;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "task")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "task" }, allowSetters = true)
    private Set<TaskResult> results = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Task id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommand() {
        return this.command;
    }

    public Task command(String command) {
        this.setCommand(command);
        return this;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getImplantTaskId() {
        return this.implantTaskId;
    }

    public Task implantTaskId(Integer implantTaskId) {
        this.setImplantTaskId(implantTaskId);
        return this;
    }

    public void setImplantTaskId(Integer implantTaskId) {
        this.implantTaskId = implantTaskId;
    }

    public String getSubmittedBy() {
        return this.submittedBy;
    }

    public Task submittedBy(String submittedBy) {
        this.setSubmittedBy(submittedBy);
        return this;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    public String getDescription() {
        return this.description;
    }

    public Task description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getAdded() {
        return this.added;
    }

    public Task added(Instant added) {
        this.setAdded(added);
        return this;
    }

    public void setAdded(Instant added) {
        this.added = added;
    }

    public Instant getUpdated() {
        return this.updated;
    }

    public Task updated(Instant updated) {
        this.setUpdated(updated);
        return this;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }

    public Boolean getRetrieved() {
        return this.retrieved;
    }

    public Task retrieved(Boolean retrieved) {
        this.setRetrieved(retrieved);
        return this;
    }

    public void setRetrieved(Boolean retrieved) {
        this.retrieved = retrieved;
    }

    public Boolean getFailure() {
        return this.failure;
    }

    public Task failure(Boolean failure) {
        this.setFailure(failure);
        return this;
    }

    public void setFailure(Boolean failure) {
        this.failure = failure;
    }

    public Boolean getApproved() {
        return this.approved;
    }

    public Task approved(Boolean approved) {
        this.setApproved(approved);
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Set<TaskResult> getResults() {
        return this.results;
    }

    public void setResults(Set<TaskResult> taskResults) {
        if (this.results != null) {
            this.results.forEach(i -> i.setTask(null));
        }
        if (taskResults != null) {
            taskResults.forEach(i -> i.setTask(this));
        }
        this.results = taskResults;
    }

    public Task results(Set<TaskResult> taskResults) {
        this.setResults(taskResults);
        return this;
    }

    public Task addResult(TaskResult taskResult) {
        this.results.add(taskResult);
        taskResult.setTask(this);
        return this;
    }

    public Task removeResult(TaskResult taskResult) {
        this.results.remove(taskResult);
        taskResult.setTask(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        return id != null && id.equals(((Task) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", command='" + getCommand() + "'" +
            ", implantTaskId=" + getImplantTaskId() +
            ", submittedBy='" + getSubmittedBy() + "'" +
            ", description='" + getDescription() + "'" +
            ", added='" + getAdded() + "'" +
            ", updated='" + getUpdated() + "'" +
            ", retrieved='" + getRetrieved() + "'" +
            ", failure='" + getFailure() + "'" +
            ", approved='" + getApproved() + "'" +
            "}";
    }
}
