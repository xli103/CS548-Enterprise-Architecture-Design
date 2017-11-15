package edu.stevens.cs548.clinic.test;

import java.util.Calendar;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.Specialization;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;

/**
 * Session Bean implementation class TestBean
 */
@Singleton
@LocalBean
@Startup
public class InitBean {

	private static Logger logger = Logger.getLogger(InitBean.class.getCanonicalName());

	/**
	 * Default constructor.
	 */
	public InitBean() {
	}
	
	// TODO inject an EM
//	@PersistenceContext(unitName="ClinicDomain")
//	EntityManager em;
	@Inject
	private IPatientServiceLocal service;
	@Inject
	private IProviderServiceLocal servicepro;
	
	@PostConstruct
	private void init() {
		/*
		 * Put your testing logic here. Use the logger to display testing output in the server logs.
		 */
		logger.info("Your name here: Xinghan Li");

		try {

			Calendar calendar = Calendar.getInstance();
			calendar.set(1984, 9, 4);
			Calendar calendar1=Calendar.getInstance();
			calendar1.set(1990,9,3);
			
//			IPatientDAO patientDAO = new PatientDAO(em);
//			ITreatmentDAO treatmentDAO = new TreatmentDAO(em);
//			IProviderDAO providerDAO = new ProviderDAO(em);

			PatientFactory patientFactory = new PatientFactory();
			TreatmentFactory treatmentFactory = new TreatmentFactory();
			ProviderFactory providerFactory = new ProviderFactory();
			PatientDtoFactory patientDtoFactory = new PatientDtoFactory();
			ProviderDtoFactory providerDtoFactory = new ProviderDtoFactory();
			TreatmentDtoFactory treatmentDtoFactory = new TreatmentDtoFactory();
			/*
			 * Clear the database and populate with fresh data.
			 * 
			 * If we ensure that deletion of patients cascades deletes of treatments,
			 * then we only need to delete patients.
			 */
			
			Patient john = patientFactory.createPatient(12345678L, "John Doe", calendar.getTime(), 32);
			Patient sam = patientFactory.createPatient(22345678L, " Sam Doe", calendar1.getTime(), 26);
			PatientDto j=patientDtoFactory.createPatientDto(sam);
			PatientDto p = patientDtoFactory.createPatientDto(john);
			long samDid=service.addPatient(j);
			long johnDid=service.addPatient(p);
			logger.info("Added "+j.getName()+" with id "+samDid);
			logger.info("Added "+p.getName()+" with id "+johnDid);
			
			// TODO add more testing, including treatments and providers
			
			/*
			 * Retrieve a patient aggregate from the system, given the primary key for the patient.
			 */
			 p =service.getPatient(johnDid);
			logger.info("Retrieve a patient with primary key: "+johnDid);
			logger.info("Patient name: "+ p.getName()+"patientId: "+p.getPatientId());
			
			/*
			 * Retrieve a patient aggregate from the system, given the patient identifier for the patient. 
			 */
			service.getPatientByPatId(p.getPatientId());
			logger.info("Retrive a patient with patientID: "+john.getPatientId()+". Patient name: "+ p.getName()+", primary key: "+p.getId());
			
			
			/*
			 * Add a provider to a clinic
			 */
			
			Provider mike = providerFactory.createProvider(66666666L, "Mike Murphy", Specialization.Oncology);
			ProviderDto providerDto = providerDtoFactory.createProviderDto(mike);
			long stevenDid=servicepro.addProvider(providerDto);
			logger.info("Added " +mike.getName()+" with id "+stevenDid);
			
			/*
			 * Retrieve a provider aggregate from the system, given the primary key for the provider
			 */
			ProviderDto pr = servicepro.getProvider(stevenDid);
			logger.info("Retrieve a provider with primary key: "+ stevenDid+". Name: "+pr.getName()
			+", specialization: "+pr.getSpecialization());
			
			/*
			 * Retrieve a provider aggregate from the system, given the provider identifier for the provider.
			 */
			servicepro.getProviderByProId(providerDto.getProviderId());
			logger.info("Retrive a provider with providerID: " + mike.getProviderId()+". Name: "+pr.getName()
			+", specialization: "+pr.getSpecialization());
			
			/*
			 * add a treatment for a patient
			 */
			
			//Use the treatment factory to create the entity.
			Treatment t = treatmentFactory.createDrugTreatment( "Asiprin", 2);
			t.setPatient(john);
			t.setProvider(mike);
			TreatmentDto tdto = treatmentDtoFactory.createTreatmentDto((DrugTreatment)t);
			//add treat t to a patient p.
			long tDid =servicepro.addDrugTreatment(tdto);
			
			logger.info("Added treatment "+t.getTreatmentType() +" to paient "+p.getName() +", treatmentId: "+tDid);
			
			/*
			 * return a treatmentDto, given a patient key and a treatmentkey
			 */
			service.getTreatment(p.getId(), tDid);
			logger.info("get the treatmentDto from patient id: "+p.getId()+", treatmentId: "+tDid);
			
			
		} catch (PatientExn e) {

			// logger.log(Level.SEVERE, "Failed to add patient record.", e);
			IllegalStateException ex = new IllegalStateException("Failed to add patient record.");
			ex.initCause(e);
			throw ex;
			
		} catch (ProviderExn e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProviderServiceExn e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PatientServiceExn e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
	}

}
