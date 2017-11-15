package edu.stevens.cs548.clinic.test;

import java.util.Calendar;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
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
    
	@Inject
	IPatientServiceLocal patientService;
	
	@Inject
	IProviderServiceLocal providerService;

	@PostConstruct
	private void init() {
		/*
		 * Put your testing logic here. Use the logger to display testing output in the server logs.
		 */
		logger.info("Your name here: Xinghan Li");

		try {
			
			PatientFactory patientFactory = new PatientFactory();
			ProviderFactory providerFactory = new ProviderFactory();
			TreatmentFactory treatmentFactory = new TreatmentFactory();
			PatientDtoFactory patDtoFactory = new PatientDtoFactory();

			Calendar calendar = Calendar.getInstance();
			calendar.set(1984, 9, 4);
						
			/*
			 * TODO Clear the database and populate with fresh data.
			 * 
			 * TODO Do testing with patients, providers and treatments.
                         * Write results of testing to the logs.
			 */
			
			Patient john = patientFactory.createPatient(12345678L, "John Doe", calendar.getTime(), 32);
			PatientDto patDto = new PatientDto();
			patDto = patDtoFactory.createPatientDto(john);
		//	patDto.setDob(calendar.getTime());
		//	patDto.setId(12345678L);
		//	patDto.setName("John Doe");
		//	patDto.setPatientId(11111111L);
			patientService.addPatient(patDto);
			
			logger.info("Added patient John: " );
			
		//	Provider Mike = providerFactory.createProvider(66666666L, "Mike Murphy", "pharmacist");
			ProviderDto provDto = new ProviderDto();
			provDto.setId(22222222L);
			provDto.setName("Mike Murphy");
			provDto.setNpi(66666666L);
			provDto.setSpecialization("pharmacist");
			providerService.addProvider(provDto);
			
			logger.info("Added provider mike: " );
			
	/*		DrugTreatmentType dt = new DrugTreatmentType();
			dt.setDosage(2);
			dt.setName("Aspirin");
			TreatmentDto tdto = new TreatmentDto();
			tdto.setDiagnosis("Fever");
			tdto.setId(00000001L);
			tdto.setPatient(patDto.getPatientId());
			tdto.setProvider(provDto.getNpi());
			tdto.setDrugTreatment(dt);
			providerService.addDrugTreatment(tdto);
			
			logger.info("Added treatment: ");
			*/
			
			
			
		} catch (PatientServiceExn e) {

			// logger.log(Level.SEVERE, "Failed to add patient record.", e);
			IllegalStateException ex = new IllegalStateException("Failed to add patient record.");
			ex.initCause(e);
			throw ex;
			
		} catch(ProviderServiceExn e){
			IllegalStateException ex = new IllegalStateException("Failed to add patient record.");
			ex.initCause(e);
			throw ex;
		} catch(PatientExn e){
			IllegalStateException ex = new IllegalStateException("Failed to add patient record.");
			ex.initCause(e);
			throw ex;
		}
			
	}

}
