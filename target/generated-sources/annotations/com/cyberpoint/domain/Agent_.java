package com.cyberpoint.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Agent.class)
public abstract class Agent_ {

	public static volatile SingularAttribute<Agent, RegistrationEvent> registrationEvent;
	public static volatile SingularAttribute<Agent, Boolean> autoRegistered;
	public static volatile SingularAttribute<Agent, Instant> installedOn;
	public static volatile SingularAttribute<Agent, Boolean> approved;
	public static volatile SingularAttribute<Agent, Instant> uninstallDate;
	public static volatile SingularAttribute<Agent, String> name;
	public static volatile SingularAttribute<Agent, String> description;
	public static volatile SingularAttribute<Agent, Boolean> active;
	public static volatile SingularAttribute<Agent, Long> id;
	public static volatile SingularAttribute<Agent, String> classification;
	public static volatile SingularAttribute<Agent, Platform> platform;
	public static volatile SingularAttribute<Agent, Boolean> deactivate;

	public static final String REGISTRATION_EVENT = "registrationEvent";
	public static final String AUTO_REGISTERED = "autoRegistered";
	public static final String INSTALLED_ON = "installedOn";
	public static final String APPROVED = "approved";
	public static final String UNINSTALL_DATE = "uninstallDate";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String ACTIVE = "active";
	public static final String ID = "id";
	public static final String CLASSIFICATION = "classification";
	public static final String PLATFORM = "platform";
	public static final String DEACTIVATE = "deactivate";

}

