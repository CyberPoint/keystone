package com.cyberpoint.domain;

import jakarta.persistence.metamodel.SetAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Task.class)
public abstract class Task_ {

	public static volatile SingularAttribute<Task, String> submittedBy;
	public static volatile SingularAttribute<Task, Boolean> approved;
	public static volatile SingularAttribute<Task, Agent> agent;
	public static volatile SingularAttribute<Task, String> formattedCommand;
	public static volatile SingularAttribute<Task, Instant> added;
	public static volatile SingularAttribute<Task, Boolean> failure;
	public static volatile SingularAttribute<Task, String> description;
	public static volatile SingularAttribute<Task, Boolean> retrieved;
	public static volatile SingularAttribute<Task, Long> id;
	public static volatile SingularAttribute<Task, Instant> updated;
	public static volatile SetAttribute<Task, TaskResult> results;
	public static volatile SingularAttribute<Task, String> command;

	public static final String SUBMITTED_BY = "submittedBy";
	public static final String APPROVED = "approved";
	public static final String AGENT = "agent";
	public static final String FORMATTED_COMMAND = "formattedCommand";
	public static final String ADDED = "added";
	public static final String FAILURE = "failure";
	public static final String DESCRIPTION = "description";
	public static final String RETRIEVED = "retrieved";
	public static final String ID = "id";
	public static final String UPDATED = "updated";
	public static final String RESULTS = "results";
	public static final String COMMAND = "command";

}

