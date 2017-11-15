package edu.stevens.cs548.clinic.service.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="patient-dto", namespace="http://www.example.org/clinic/schemas/patient")
public class PatientDTO {

	public long id;
	
	@XmlElement(name="patient-id")
	public long PatientId;
	
	@XmlElement(required=true)
	public String name;
	
	@XmlElement(name="dob", required=true)
	public Date dob;
	
	public long[] treatments;
	
}