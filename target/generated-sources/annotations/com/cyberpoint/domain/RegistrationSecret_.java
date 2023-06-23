package com.cyberpoint.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RegistrationSecret.class)
public abstract class RegistrationSecret_ {

	public static volatile SingularAttribute<RegistrationSecret, RegistrationEvent> registrationEvent;
	public static volatile SingularAttribute<RegistrationSecret, Integer> numericalValue;
	public static volatile SingularAttribute<RegistrationSecret, String> uniqueValue;
	public static volatile SingularAttribute<RegistrationSecret, Long> id;

	public static final String REGISTRATION_EVENT = "registrationEvent";
	public static final String NUMERICAL_VALUE = "numericalValue";
	public static final String UNIQUE_VALUE = "uniqueValue";
	public static final String ID = "id";

}

