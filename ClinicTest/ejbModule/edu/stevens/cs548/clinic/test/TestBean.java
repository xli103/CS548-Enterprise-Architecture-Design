package edu.stevens.cs548.clinic.test;

import java.util.Calendar;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.TreatmentDAO;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;

/**
 * Session Bean implementation class TestBean
 */
@Singleton
@LocalBean
@Startup
public class TestBean {
	
	private static Logger logger = Logger.getLogger(TestBean.class.getCanonicalName());
	@SuppressWarnings("unused")
	private static void info(String m) {
		logger.info(m);
		}

    /**
     * Default constructor. 
     */
    public TestBean() {
        // TODO Auto-generated constructor stub
    }
    
 // inject an EM
 	@PersistenceContext(unitName = "ClinicDomain")
 	private EntityManager em;

 	@PostConstruct
 	private void init() {
 		/*
 		 * Put your testing logic here. Use the logger to display testing output in the server logs.
 		 */
 		logger.info("Your name here: Xinghan Li");

 		try {

 			Calendar calendar = Calendar.getInstance();
 			calendar.set(1984, 9, 4);
 			
 			IPatientDAO patientDAO = new PatientDAO(em);
 			IProviderDAO providerDAO = new ProviderDAO(em);
 			ITreatmentDAO treatmentDAO = new TreatmentDAO(em);

 			PatientFactory patientFactory = new PatientFactory();
 			ProviderFactory providerFactory = new ProviderFactory();
 			TreatmentFactory treatmentFactory = new TreatmentFactory();
 			
 			/*
 			 * Clear the database and populate with fresh data.
 			 * 
 			 * If we ensure that deletion of patients cascades deletes of treatments,
 			 * then we only need to delete patients.
 			 */
 			
 			patientDAO.deletePatients();
 			
 			Patient john = patientFactory.createPatient(12345678L, "John Doe", calendar.getTime(), 30);
 			patientDAO.addPatient(john);
 			
 			logger.info("Added patient "+john.getName()+" with id "+john.getId());
 			
 			// TODO add more testing, including treatments and providers
 			providerDAO.deleteProviders();
 			Provider mike = providerFactory.createProvider(66666666L, "Mike Murphy", "pharmacist");
 			providerDAO.addProvider(mike);
 			
 			logger.info("Added provider "+mike.getName()+" with npi "+mike.getNpi());
 			
 			Treatment t1 = treatmentFactory.createDrugTreatment(00000001L, "Fever", "Aspirin", 2, john);
 			logger.info("Created treatment record "+ t1.getId() +" with diagnosis "+ t1.getDiagnosis());
 			
 			mike.addTreatment(t1);
 			
 		} catch (PatientExn | ProviderExn e) {

 			// logger.log(Level.SEVERE, "Failed to add patient record.", e);
 			IllegalStateException ex = new IllegalStateException("Failed to add patient record.");
 			ex.initCause(e);
 			throw ex;
 			
 		} 
 	}

}
