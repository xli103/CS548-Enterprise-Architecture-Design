package edu.stevens.cs548.clinic.test;

import java.util.Calendar;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.*;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;

/**
 * Session Bean implementation class TestBean
 */
@Singleton
@LocalBean
@Startup
public class InitBean {

	private static Logger logger = Logger.getLogger(InitBean.class.getCanonicalName());
	private static void info(String m) {
		logger.info(m);
		}
	/**
	 * Default constructor.
	 */
	public InitBean() {
	}
	

	@PersistenceContext(unitName = "ClinicDomain")
	private EntityManager em;

	@PostConstruct
	private void init() {

		info("Initializing the user database.");
		logger.info("Your name here: wentao zhang");

		try {

			Calendar calendar = Calendar.getInstance();
			calendar.set(1984, 9, 4);
			
			IPatientDAO patientDAO = new PatientDAO(em);
			IProviderDAO providerDAO = new ProviderDAO(em);
			ITreatmentDAO tdao = new TreatmentDAO(em);

			PatientFactory patientFactory = new PatientFactory();
			ProviderFactory providerFactory = new ProviderFactory();
			TreatmentFactory treatmentFactory = new TreatmentFactory();

			
			patientDAO.deletePatients();
			
			Patient tom = patientFactory.createPatient(56781234L, "Tom Chen", calendar.getTime(), 32);
			patientDAO.addPatient(tom);
			
			logger.info("Added patient "+tom.getName()+" with id "+tom.getId());
			
			// TODO add more testing, including treatments and providers
			providerDAO.deleteProviders();
			Provider bob = providerFactory.createProvider(11223344L, "Bob Liu", "pharmacist");
			providerDAO.addProvider(bob);
			
			logger.info("Added provider "+bob.getName()+" with npi "+bob.getNpi());
			
			Treatment t1 = treatmentFactory.createDrugTreatment("Cold", "Coke", 2);
			t1.setPatient(tom);
			
			bob.addTreatment(t1);
			logger.info("Created treatment record "+ t1.getDiagnosis() +"\n retrive: id=" + t1.getId() + " diagnosis=" + t1.getDiagnosis() + " type=" + t1.getTreatmentType());

			
			
			
			
		} catch (PatientExn | ProviderExn e) {

			// logger.log(Level.SEVERE, "Failed to add patient record.", e);
			IllegalStateException ex = new IllegalStateException("Failed to add patient record.");
			ex.initCause(e);
			throw ex;
			
		} 
			
	}

}
