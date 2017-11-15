package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import static javax.persistence.CascadeType.REMOVE;

/**
 * Entity implementation class for Entity: Patient
 *
 */
@Entity


public class Patient implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private long id;
	private long patientId;
	private String name;
	private Date birthDate;
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public long getPatientId(){
		return patientId;
	}
	
	public void setPatientId(long patientId){
		this.patientId = patientId;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public Date getBirthDate(){
		return birthDate;
	}
	
	public void setBrithDate(Date birthDate){
		this.birthDate = birthDate;
	}
	

	@OneToMany(mappedBy = "patient", cascade = REMOVE)
	@OrderBy
	private List<Treatment> treatments;
	
	public List<Treatment> getTreatments(){
		return treatments;
	}
	
	public void setTreatments(List<Treatment> treatments){
		this.treatments = treatments;
	}
	
	public Patient() {
		super();
		treatments = new ArrayList<Treatment>();
	}
   
}
