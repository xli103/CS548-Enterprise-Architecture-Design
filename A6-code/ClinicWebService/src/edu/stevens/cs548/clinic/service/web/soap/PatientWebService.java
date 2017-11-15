package edu.stevens.cs548.clinic.service.web.soap;

import java.util.Date;

import javax.ejb.EJB;
import javax.jws.WebService;

import edu.stevens.cs548.clinic.service.dto.PatientDTO;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.service.ejb.IPatientServiceRemote;
import edu.stevens.cs548.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.service.ejb.IPatientService.TreatmentNotFoundExn;

@WebService(
		endpointInterface="edu.stevens.cs548.clinic.service.web.soap.IPatientWebService",
		serviceName="PatientWeb")
public class PatientWebService implements IPatientWebService {

	@EJB(beanName="PatientServiceBean")
	IPatientServiceRemote service;
	@Override
	public long createPatient(String name, Date dob, long patientId) throws PatientServiceExn {
		return service.createPatient(name, dob, patientId);
	}

	@Override
	public PatientDTO getPatientByDbId(long id) throws PatientServiceExn {
		return service.getPatientByDbId(id);
	}

	@Override
	public PatientDTO getPatientByPatId(long pid) throws PatientServiceExn {
		return service.getPatientByPatId(pid);
	}

	@Override
	public PatientDTO[] getPatientByNameDob(String name, Date dob) {
		return service.getPatientByNameDob(name, dob);
	}

	@Override
	public void deletePatient(String name, long id) throws PatientServiceExn {
		service.deletePatient(name, id);
	}

	@Override
	public long addDrugTreatment(long id, String diagnosis, String drug, float dosage) throws PatientNotFoundExn {
		return service.addDrugTreatment(id, diagnosis, drug, dosage);
	}

	@Override
	public TreatmentDto[] getTreatments(long id, long[] tids)
			throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn {
		return service.getTreatments(id, tids);
	}

	@Override
	public void deleteTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn {
		service.deleteTreatment(id, tid);
	}

	@Override
	public String siteInfo() {
		return service.siteInfo();
	}

	
	
	
}
