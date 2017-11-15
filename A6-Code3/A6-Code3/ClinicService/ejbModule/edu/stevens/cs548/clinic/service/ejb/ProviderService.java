package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import edu.stevens.cs548.clinic.domain.IPatientDAO;
import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.IProviderFactory;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.ITreatmentExporter;
import edu.stevens.cs548.clinic.domain.ITreatmentFactory;
import edu.stevens.cs548.clinic.domain.PatientDAO;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.Specialization;
import edu.stevens.cs548.clinic.domain.Treatment;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;

/**
 * Session Bean implementation class ProviderService
 */
@Stateless(name = "ProviderServiceBean")
public class ProviderService implements IProviderServiceLocal, IProviderServiceRemote {

	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(ProviderService.class.getCanonicalName());

	private IProviderFactory providerFactory;

	private ProviderDtoFactory providerDtoFactory;

	private IProviderDAO providerDAO;
	
	private IPatientDAO patientDAO;
	
	private ITreatmentFactory treatmentFactory;

	
    /**
     * Default constructor. 
     */
    public ProviderService() {
        providerFactory = new ProviderFactory();
        providerDtoFactory = new ProviderDtoFactory();
        treatmentFactory = new TreatmentFactory();
    }
    
    @Inject
	@ClinicDomain
	private EntityManager em;
    
    @PostConstruct
   	private void initialization() {
   		providerDAO = new ProviderDAO(em);
   		patientDAO = new PatientDAO(em);
   	}
    
	/**
     * @see IProviderService#addProvider(ProviderDto)
     */
    public long addProvider(ProviderDto dto) throws ProviderServiceExn{
    		try {
				Provider provider = providerFactory.createProvider(dto.getProviderId(), dto.getName(), Specialization.valueOf(dto.getSpecialization()));
				providerDAO.addProvider(provider);
				return provider.getId();
			} catch (ProviderExn e) {
				throw new ProviderServiceExn(e.toString());
			}
    }
    
	/**
     * @see IProviderService#getProviderByPatId(long)
     */
    public ProviderDto getProviderByProId(long pid) throws ProviderServiceExn{
        	try{
        		Provider provider = providerDAO.getProviderByProviderId(pid);
        		return providerDtoFactory.createProviderDto(provider);
        	} catch(ProviderExn e){
        		throw new ProviderServiceExn(e.toString());
        	}
    }

	/**
     * @see IProviderService#addDrugTreatment(TreatmentDto)
     */
    public long addDrugTreatment(TreatmentDto t) throws ProviderServiceExn{
        	try {
				Provider provider = providerDAO.getProviderByProviderId(t.getProvider());
				String diagnosis = t.getDiagnosis();
				String drugname = t.getDrugTreatmentType().getName();
				float dosage = t.getDrugTreatmentType().getDosage();
				Treatment drugTreatment = treatmentFactory.createDrugTreatment(drugname, dosage);
				drugTreatment.setPatient(patientDAO.getPatientByPatientId(t.getPatient()));
				provider.addTreatment(drugTreatment);
				return drugTreatment.getId();
			} catch (ProviderExn e) {
				throw new ProviderServiceExn(e.toString());
			} catch (PatientExn f) {
				throw new ProviderServiceExn(f.toString());
			}
    }
	/**
     * @see IProviderService#addSurgery(TreatmentDto)
     */
    public long addSurgery(TreatmentDto t) throws ProviderServiceExn{
    		try{
    			Provider provider = providerDAO.getProviderByProviderId(t.getProvider());
    			String diagnosis = t.getDiagnosis();
    			Date date = t.getSurgery().getDate();
    			Treatment surgery = treatmentFactory.createSurgery( date);
    			surgery.setPatient(patientDAO.getPatientByPatientId(t.getPatient()));
    			return provider.addTreatment(surgery);
    		} catch(ProviderExn e){
    			throw new ProviderServiceExn(e.toString());
    		} catch(PatientExn f){
    			throw new ProviderServiceExn(f.toString());
    		}
    }
    /**
     * @see IProviderService#addRadiology(TreatmentDto)
     */
    public long addRadiology(TreatmentDto t) throws ProviderServiceExn{
    		try{
    			Provider provider = providerDAO.getProviderByProviderId(t.getProvider());
    			String diagnosis = t.getDiagnosis();
    			List<Date> list = t.getRadiology().getDate();
    			Treatment radiology = treatmentFactory.createRadiology( list);
    			radiology.setPatient(patientDAO.getPatientByPatientId(t.getPatient()));
    			return provider.addTreatment(radiology);
    		} catch(ProviderExn e){
    			throw new ProviderServiceExn(e.toString());
    		} catch (PatientExn f){
    			throw new ProviderServiceExn(f.toString());
    		}
    }

	/**
     * @see IProviderService#getProivder(long)
     */
    public ProviderDto getProvider(long id) throws ProviderServiceExn{
           	try{
           		Provider provider = providerDAO.getProvider(id);
           		return providerDtoFactory.createProviderDto(provider);
           	} catch (ProviderExn e){
           		throw new ProviderServiceExn(e.toString());
           	}
    }

	

    public class TreatmentExporter implements ITreatmentExporter<TreatmentDto> {

		private ObjectFactory factory = new ObjectFactory();

		@Override
		public TreatmentDto exportDrugTreatment(long tid, Provider p, String diagnosis, String drug, float dosage) {
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setDiagnosis(diagnosis);
			DrugTreatmentType drugInfo = factory.createDrugTreatmentType();
			drugInfo.setDosage(dosage);
			drugInfo.setName(drug);
			dto.setDrugTreatmentType(drugInfo);
			return dto;
		}

		@Override
		public TreatmentDto exportRadiology(long tid, Provider p, String diagnosis, List<Date> dates) {
			// TODO Auto-generated method stub
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setDiagnosis(diagnosis);
			RadiologyType radiology = factory.createRadiologyType();
			radiology.getDate().addAll(dates);
			dto.setRadiology(radiology);
			return dto;
		}

		@Override
		public TreatmentDto exportSurgery(long tid, Provider p, String diagnosis, Date date) {
			// TODO Auto-generated method stub
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setDiagnosis(diagnosis);
			SurgeryType surgery = factory.createSurgeryType();
			surgery.setDate(date);
			dto.setSurgery(surgery);
			return dto;
		}

	}
    
    @Resource(name = "SiteInfo")
	private String siteInformation;
	/**
     * @see IProviderService#siteInfo()
     */
    public String siteInfo() {
			return siteInformation;
    }

	@Override
	public TreatmentDto getTreatment(long id, long tid) throws ProviderNotFoundExn, ProviderServiceExn, TreatmentNotFoundExn{
		// TODO Auto-generated method stub
		try {
			Provider provider = providerDAO.getProvider(id);
			TreatmentExporter visitor = new TreatmentExporter();
			return provider.exportTreatment(tid, visitor);
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new ProviderServiceExn(e.toString());
		}
	}

}
