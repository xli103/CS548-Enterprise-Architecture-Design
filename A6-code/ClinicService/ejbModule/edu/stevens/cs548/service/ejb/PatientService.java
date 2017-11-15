package edu.stevens.cs548.service.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.ITreatmentVisitor;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.PatientDTO;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

/**
 * Session Bean implementation class PatientService
 */
@Stateless(name="PatientServiceBean")
public class PatientService implements IPatientServiceLocal, IPatientServiceRemote {

	private PatientFactory patientFactory;
	
	private IPatientDAO patientDAO;

    /**
     * Default constructor. 
     */
    public PatientService() {
        patientFactory = new PatientFactory();
    }
	
	@PersistenceContext(unitName="ClinicDomain")
	private EntityManager em;
	
	@PostConstruct
	private void initialize(){
		patientDAO = new PatientDAO(em);
	}

	private PatientDTO patientToDTO(Patient patient){
		return new PatientDTO(patient);
	}
	/**
     * @see IPatientService#getPatientByDbId(long)
     */
    public PatientDTO getPatientByDbId(long id) throws PatientServiceExn {
        // TODO Auto-generated method stub
    	try{
    		Patient patient = patientDAO.getPatientByPatientId(id);
    		return new PatientDTO(patient);
    	}catch(PatientExn e){
    		throw new PatientServiceExn(e.toString());
    	}
    }

	/**
     * @see IPatientService#getPatientByPatId(long)
     */
    public PatientDTO getPatientByPatId(long pid) throws PatientServiceExn {
        // TODO Auto-generated method stub
    	try{
    		Patient patient = patientDAO.getPatinetByPatientId(pid);
    		return new PatientDTO(patient);
    	}catch(PatientExn e){
    		throw new PatientServiceExn(e.toString());
    	}
    }
    
	/**
     * @see IPatientService#getPatientByNameDob(String, Date)
     */
    public PatientDTO[] getPatientByNameDob(String name, Date dob) {
        // TODO Auto-generated method stub
    	List<Patient> patients = patientDAO.getPatientByNameDob(name, dob);
		PatientDTO[] dto = new PatientDTO[patients.size()];
		for(int i=0; i<dto.length; i++){
			dto[i] = new PatientDTO(patients.get(i));
		}
		return dto;
    }

	/**
     * @see IPatientService#createPatient(String, Date, long)
     */
    public long createPatient(String name, Date dob, long patientId) throws PatientServiceExn {
        // TODO Auto-generated method stub
    	Patient patient = this.patientFactory.createPatient(patientId, name, dob);
    	try{
    		patientDAO.addPatient(patient);
    	}catch(PatientExn e){
    		throw new PatientServiceExn(e.toString());
    	}
			return patient.getId();
    }

	/**
     * @see IPatientService#deletePatient(String, long)
     */
    public void deletePatient(String name, long id) throws PatientServiceExn {
        // TODO Auto-generated method stub
    	try{
    		Patient patient = patientDAO.getPatientByPatientId(id);
    		if(!name.equals(patient.getName())) {
    			throw new PatientServiceExn("Tried to delete wrong patient: name = " + name + ", id= " + id);
    		}
    		else{
    			patientDAO.deletePatient(patient);
    		}
    	}catch(PatientExn e){
    		throw new PatientServiceExn(e.toString());
    	}
    }

	@Override
	public long addDrugTreatment(long id, String diagnosis, String drug, float dosage) throws PatientNotFoundExn {
		// TODO Auto-generated method stub
		try{
			Patient patient = patientDAO.getPatientByPatientId(id);
			patient.addDrugTreatment(diagnosis, drug, dosage);
			return patient.getTreatmentIds().get(0);
		}catch(PatientExn e){
			throw new PatientNotFoundExn(e.toString());
		}
	}

	static class TreatmentPDOtoDTO implements ITreatmentVisitor{
		
		private TreatmentDto dto;
		public TreatmentDto getDTO(){
			return dto;
		}

		@Override
		public void visitDrugTreatment(long tid, String diagnosis, String drug, float dosage) {
			// TODO Auto-generated method stub
			dto = new TreatmentDto();
			dto.setDiagnosis(diagnosis);
			DrugTreatmentType drugInfo = new DrugTreatmentType();
			drugInfo.setDosage(dosage);
			drugInfo.setName(drug);
			dto.setDrugTreatment(drugInfo);
		}

		@Override
		public void visitRadiology(long tid, String diagnosis, List<Date> dates) {
			// TODO Auto-generated method stub
			dto = new TreatmentDto();
			dto.setDiagnosis(diagnosis);
			RadiologyType radioInfo = new RadiologyType();
		}

		@Override
		public void visitSurgery(long tid, String diagnosis, Date date) {
			// TODO Auto-generated method stub
			dto = new TreatmentDto();
			dto.setDiagnosis(diagnosis);
			SurgeryType surInfo = new SurgeryType();
			surInfo.setDate(date);
			dto.setSurgery(surInfo);
		}
		
	}
	
	@Override
	public TreatmentDto[] getTreatments(long id, long[] tids)
			throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn {
		// TODO Auto-generated method stub
		try{
			Patient patient = patientDAO.getPatientByPatientId(id);
			TreatmentDto[] treatments = new TreatmentDto[tids.length];
			TreatmentPDOtoDTO visitor = new TreatmentPDOtoDTO();
			for(int i=0; i<tids.length; i++){
				patient.visitTreatment(tids[i], visitor);
				treatments[i] = visitor.getDTO();
			}
			return treatments;
		}catch(PatientExn e){
			throw new PatientNotFoundExn(e.toString());
		}catch(TreatmentExn e){
			throw new PatientServiceExn(e.toString());
		}
	}

	@Override
	public void deleteTreatment(long id, long tid) throws PatientNotFoundExn, TreatmentNotFoundExn, PatientServiceExn {
		// TODO Auto-generated method stub
		try{
			Patient patient = patientDAO.getPatientByPatientId(id);
			patient.deleteTreatment(tid);
		}catch(PatientExn e){
			throw new PatientNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new TreatmentNotFoundExn(e.toString());
		}
	}
	
	@Resource(name="SiteInfo")
	private String siteInformation;

	@Override
	public String siteInfo() {
		return siteInformation;
	}

	@Override
	public long addRadiologyTreatment(long id, String diagnosis, List<Date> dates) throws PatientNotFoundExn {
		// TODO Auto-generated method stub
		try{
			Patient patient = patientDAO.getPatientByPatientId(id);
			patient.addRadiologyTreatment(diagnosis, dates);
			return patient.getTreatmentIds().get(0);
		}catch(PatientExn e){
			throw new PatientNotFoundExn(e.toString());
		}
	}

	@Override
	public long addSurgeryTreatment(long id, String diagnosis, Date date) throws PatientNotFoundExn {
		// TODO Auto-generated method stub
		try{
			Patient patient = patientDAO.getPatientByPatientId(id);
			patient.addSurgeryTreatment(diagnosis, date);
			return patient.getTreatmentIds().get(0);
		}catch(PatientExn e){
			throw new PatientNotFoundExn(e.toString());
		}
	}

}
