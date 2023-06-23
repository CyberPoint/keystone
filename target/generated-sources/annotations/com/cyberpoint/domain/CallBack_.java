package com.cyberpoint.domain;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CallBack.class)
public abstract class CallBack_ {

	public static volatile SingularAttribute<CallBack, Agent> agent;
	public static volatile SingularAttribute<CallBack, String> ipAddress;
	public static volatile SingularAttribute<CallBack, Integer> remotePort;
	public static volatile SingularAttribute<CallBack, byte[]> rawcontents;
	public static volatile SingularAttribute<CallBack, String> rawcontentsContentType;
	public static volatile SingularAttribute<CallBack, Long> id;
	public static volatile SingularAttribute<CallBack, String> buffer;
	public static volatile SingularAttribute<CallBack, String> url;
	public static volatile SingularAttribute<CallBack, Instant> timestamp;

	public static final String AGENT = "agent";
	public static final String IP_ADDRESS = "ipAddress";
	public static final String REMOTE_PORT = "remotePort";
	public static final String RAWCONTENTS = "rawcontents";
	public static final String RAWCONTENTS_CONTENT_TYPE = "rawcontentsContentType";
	public static final String ID = "id";
	public static final String BUFFER = "buffer";
	public static final String URL = "url";
	public static final String TIMESTAMP = "timestamp";

}

