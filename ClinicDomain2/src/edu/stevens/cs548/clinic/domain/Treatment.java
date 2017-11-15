package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Treatment
 *
 */
@Entity



public abstract class Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private long id;
	private String diagnosis;
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public String getDiagnosis(){
		return diagnosis;
	}
	
	public void setDiagnosis(String diagnosis){
		this.diagnosis = diagnosis;
	}
	
	@JoinColumn(name = "patient_fk", referencedColumnName = "id")
	private Patient patient;
	
	public Patient getPatient(){
		return patient;
	}
	
	public void setPatient(Patient patient){
		this.patient = patient;
	}
	
	public Treatment() {
		super();
		
	}
   
}
