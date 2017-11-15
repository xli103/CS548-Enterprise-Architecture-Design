package edu.stevens.cs548.clinic.domain;

import javax.annotation.Generated;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-11T01:22:06.198+0800")
@StaticMetamodel(Provider.class)
public class Provider_ {
	public static volatile SingularAttribute<Provider, EntityManager> em;
	public static volatile SingularAttribute<Provider, Long> npi;
	public static volatile SingularAttribute<Provider, String> name;
	public static volatile SingularAttribute<Provider, String> specialization;
	public static volatile ListAttribute<Provider, Treatment> treatments;
}
