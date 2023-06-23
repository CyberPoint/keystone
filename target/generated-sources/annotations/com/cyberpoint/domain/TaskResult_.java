package com.cyberpoint.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(TaskResult.class)
public abstract class TaskResult_ {

	public static volatile SingularAttribute<TaskResult, String> headers;
	public static volatile SingularAttribute<TaskResult, Task> task;
	public static volatile SingularAttribute<TaskResult, byte[]> embeddeddata;
	public static volatile SingularAttribute<TaskResult, Instant> added;
	public static volatile SingularAttribute<TaskResult, String> embeddeddataContentType;
	public static volatile SingularAttribute<TaskResult, String> ipAddress;
	public static volatile SingularAttribute<TaskResult, Boolean> reviewed;
	public static volatile SingularAttribute<TaskResult, Long> id;
	public static volatile SingularAttribute<TaskResult, String> url;

	public static final String HEADERS = "headers";
	public static final String TASK = "task";
	public static final String EMBEDDEDDATA = "embeddeddata";
	public static final String ADDED = "added";
	public static final String EMBEDDEDDATA_CONTENT_TYPE = "embeddeddataContentType";
	public static final String IP_ADDRESS = "ipAddress";
	public static final String REVIEWED = "reviewed";
	public static final String ID = "id";
	public static final String URL = "url";

}

