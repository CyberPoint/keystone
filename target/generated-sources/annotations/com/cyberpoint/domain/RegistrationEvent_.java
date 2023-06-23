package com.cyberpoint.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RegistrationEvent.class)
public abstract class RegistrationEvent_ {

	public static volatile SingularAttribute<RegistrationEvent, Boolean> approved;
	public static volatile SingularAttribute<RegistrationEvent, Agent> agent;
	public static volatile SingularAttribute<RegistrationEvent, String> rawContents;
	public static volatile SingularAttribute<RegistrationEvent, String> ipAddress;
	public static volatile SingularAttribute<RegistrationEvent, Integer> remotePort;
	public static volatile SingularAttribute<RegistrationEvent, String> name;
	public static volatile SingularAttribute<RegistrationEvent, Instant> registrationDate;
	public static volatile SingularAttribute<RegistrationEvent, Long> id;
	public static volatile SingularAttribute<RegistrationEvent, RegistrationSecret> secret;

	public static final String APPROVED = "approved";
	public static final String AGENT = "agent";
	public static final String RAW_CONTENTS = "rawContents";
	public static final String IP_ADDRESS = "ipAddress";
	public static final String REMOTE_PORT = "remotePort";
	public static final String NAME = "name";
	public static final String REGISTRATION_DATE = "registrationDate";
	public static final String ID = "id";
	public static final String SECRET = "secret";

}

