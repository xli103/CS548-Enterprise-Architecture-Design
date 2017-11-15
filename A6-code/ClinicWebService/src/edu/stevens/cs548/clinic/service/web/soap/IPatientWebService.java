package edu.stevens.cs548.clinic.service.web.soap;

import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebService;

import edu.stevens.cs548.clinic.service.dto.PatientDTO;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.service.ejb.IPatientService.TreatmentNotFoundExn;

@WebService(targetNamespace="http://www.example.org/clinic/wsdl/patient",
name="IPatientWebPort")
/*
 * Endpoint interface for the patient Web service
 */

public interface IPatientWebService {
	
	
	
	@WebMethod(operationName="create")
	public long createPatient(String name, Date dob, long patientId) throws PatientServiceExn;
	
	@WebMethod
	public PatientDTO getPatientByDbId (long id) throws PatientServiceExn;
	
	@WebMethod
	public PatientDTO getPatientByPatId (long pid) throws PatientServiceExn;
	
	@WebMethod
	public PatientDTO[] getPatientByNameDob (String name, Date dob);

	//name to make sure that patient is the right one to delete
	@WebMethod
	public void deletePatient (String name, long id) throws PatientServiceExn;
	
	@WebMethod
	public long addDrugTreatment(long id, String diagnosis, String drug, float dosage) throws PatientNotFoundExn;
	
	@WebMethod
	public TreatmentDto[] getTreatments(long id, long[] tids) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn;
	
	@WebMethod
	public void deleteTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn;
	
	@WebMethod
	public String siteInfo();

}
