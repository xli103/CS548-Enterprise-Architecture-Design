package edu.stevens.cs548.clinic.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-19T09:31:20.102+0800")
@StaticMetamodel(Provider.class)
public class Provider_ {
	public static volatile SingularAttribute<Provider, Long> id;
	public static volatile SingularAttribute<Provider, Long> providerId;
	public static volatile SingularAttribute<Provider, String> name;
	public static volatile SingularAttribute<Provider, Specialization> specialization;
	public static volatile ListAttribute<Provider, Treatment> treatments;
}
