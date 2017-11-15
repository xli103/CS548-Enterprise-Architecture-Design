package edu.stevens.cs548.clinic.domain;

import javax.annotation.Generated;
import javax.persistence.EntityManager;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2016-10-10T15:15:46.163+0800")
@StaticMetamodel(Treatment.class)
public class Treatment_ {
	public static volatile SingularAttribute<Treatment, EntityManager> em;
	public static volatile SingularAttribute<Treatment, Long> id;
	public static volatile SingularAttribute<Treatment, String> diagnosis;
	public static volatile SingularAttribute<Treatment, String> treatmentType;
	public static volatile SingularAttribute<Treatment, Patient> patient;
	public static volatile SingularAttribute<Treatment, Provider> provider;
}
