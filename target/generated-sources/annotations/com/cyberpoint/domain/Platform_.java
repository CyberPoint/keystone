package com.cyberpoint.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Platform.class)
public abstract class Platform_ {

	public static volatile SingularAttribute<Platform, Long> accessLevel;
	public static volatile SingularAttribute<Platform, byte[]> contents;
	public static volatile SingularAttribute<Platform, String> contentsContentType;
	public static volatile SingularAttribute<Platform, Instant> added;
	public static volatile SingularAttribute<Platform, String> name;
	public static volatile SingularAttribute<Platform, String> description;
	public static volatile SingularAttribute<Platform, Boolean> active;
	public static volatile SingularAttribute<Platform, Long> id;
	public static volatile SingularAttribute<Platform, String> version;
	public static volatile SingularAttribute<Platform, Instant> updated;

	public static final String ACCESS_LEVEL = "accessLevel";
	public static final String CONTENTS = "contents";
	public static final String CONTENTS_CONTENT_TYPE = "contentsContentType";
	public static final String ADDED = "added";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ACTIVE = "active";
	public static final String ID = "id";
	public static final String VERSION = "version";
	public static final String UPDATED = "updated";

}

