package edu.stevens.cs548.clinic.service.ejb;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.service.dto.PatientDTO;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class PatientService
 */
@Stateless
@LocalBean
public class PatientService implements IPatientServiceLocal, IPatientServiceRemote {
	
	private PatientFactory patientFactory;
	
	private IPatientDAO patientDAO;
	

    /**
     * Default constructor. 
     */
    public PatientService() {
        // TODO Auto-generated constructor stub
    	patientFactory = new PatientFactory();
    }

	
	@PersistenceContext(unitName="ClinicDomain")
	private EntityManager em;

	@PostConstruct
	private void initialize(){
		patientDAO = new PatientDAO(em);
	}
	
	/**
     * @see IPatientService#getPatientByDbId(long)
     */
    public PatientDTO getPatientByDbId(long id) throws PatientServiceExn {
        // TODO Auto-generated method stub
    	try{
    		Patient patient = patientDAO.getPatient(id);
    		PatientDTO patientDTO = 
    	}catch(PatientExn e){
    		throw new PatientServiceExn(e.toString());
    	}
			return null;
    }

	/**
     * @see IPatientService#createPatient(String, Date, long)
     */
    public long createPatient(String name, Date dob, long patientId) {
        // TODO Auto-generated method stub
			return 0;
    }

	/**
     * @see IPatientService#getPatientByPatId(long)
     */
    public PatientDTO getPatientByPatId(long id) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see IPatientService#deletePatient(String, long)
     */
    public void deletePatient(String name, long id) {
        // TODO Auto-generated method stub
    }

}
