package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;

import edu.stevens.cs548.clinic.service.dto.PatientDTO;

public interface IPatientService {
	
	public class PatientServiceExn extends Exception{
		public PatientServiceExn(String m){
			super(m);
		}
	}
	
	public long createPatient (String name, Date dob, long patientId) throws PatientServiceExn;
	
	public PatientDTO getPatientByDbId(long id) throws PatientServiceExn;
	
	public PatientDTO getPatientByPatId(long id) throws PatientServiceExn;
	
	public void deletePatient(String name, long id) throws PatientServiceExn;	

}
