package edu.stevens.cs548.clinic.service.ejb;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.IProviderFactory;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.ITreatmentExporter;
import edu.stevens.cs548.clinic.domain.ITreatmentFactory;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
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
@Stateless
@Local(IProviderServiceLocal.class)
@Remote(IProviderServiceRemote.class)
@LocalBean
public class ProviderService implements IProviderServiceLocal, IProviderServiceRemote {
	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(PatientService.class.getCanonicalName());

	private IProviderFactory providerFactory;
	
	private ITreatmentFactory treatmentFactory;
	
	private ProviderDtoFactory providerDtoFactory;
	
	private ITreatmentDAO treatmentDAO;

	private IProviderDAO providerDAO;
	
    /**
     * Default constructor. 
     */
    public ProviderService() {
        // TODO Auto-generated constructor stub
    	providerFactory = new ProviderFactory();
    	treatmentFactory = new TreatmentFactory();
    }

    @Inject
	@ClinicDomain
	private EntityManager em;
	
	@PostConstruct
	private void initialize(){
		providerDAO = new ProviderDAO(em);
	}
    

	/**
     * @see IProviderService#addProvider(ProviderDto)
     */
    public long addProvider(ProviderDto dto) throws ProviderServiceExn {
        // TODO Auto-generated method stub
    	try {
			Provider provider = providerFactory.createProvider(dto.getNpi(), dto.getName(), dto.getSpecialization());
			providerDAO.addProvider(provider);
			return provider.getNpi();
		} catch (ProviderExn e) {
			throw new ProviderServiceExn(e.toString());
		}
    }

	/**
     * @see IProviderService#getProviderByNpi(long)
     */
    public ProviderDto getProviderByNpi(long npi) throws ProviderNotFoundExn {
        // TODO Auto-generated method stub
    	try{
    		Provider provider = providerDAO.getProvider(npi);
    		ProviderDto dto = new ProviderDto();
    		dto.setName(provider.getName());
    		dto.setNpi(provider.getNpi());
    		dto.setSpecialization(provider.getSpecialization());
    		return dto;
    	}catch(ProviderExn e){
    		throw new ProviderNotFoundExn(e.toString());
    	}
    }


	/**
     * @see IProviderService#getProvider(long)
     */
    public ProviderDto getProvider(long id) throws ProviderNotFoundExn {
        // TODO Auto-generated method stub
    	try{
    		Provider provider = providerDAO.getProvider(id);
    		ProviderDto dto = new ProviderDto();
    		dto.setName(provider.getName());
    		dto.setNpi(provider.getNpi());
    		dto.setSpecialization(provider.getSpecialization());
    		return dto;
    	}catch(ProviderExn e){
    		throw new ProviderNotFoundExn(e.toString());
    	}
    }
    
    
    
	public class TreatmentExporter implements ITreatmentExporter<TreatmentDto> {
		
		private ObjectFactory factory = new ObjectFactory();
		
		@Override
		public TreatmentDto exportDrugTreatment(long tid, String diagnosis, String drug,
				float dosage) {
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setDiagnosis(diagnosis);
			DrugTreatmentType drugInfo = factory.createDrugTreatmentType();
			drugInfo.setDosage(dosage);
			drugInfo.setName(drug);
			dto.setDrugTreatment(drugInfo);
			return dto;
		}

		@Override
		public TreatmentDto exportRadiology(long tid, String diagnosis, List<Date> dates) {
			// TODO Auto-generated method stub	
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setDiagnosis(diagnosis);
			RadiologyType radioInfo = factory.createRadiologyType();
			return dto;
		}

		@Override
		public TreatmentDto exportSurgery(long tid, String diagnosis, Date date) {
			// Auto-generated method stub	
			TreatmentDto dto = factory.createTreatmentDto();
			dto.setDiagnosis(diagnosis);
			SurgeryType surInfo= factory.createSurgeryType();
			surInfo.setDate(date);
			dto.setSurgery(surInfo);
			return dto;
		}
		
	}
    
	/**
     * @see IProviderService#getTreatment(long, long)
     */
    public TreatmentDto getTreatment(long npi, long tid) 
    					throws ProviderNotFoundExn, ProviderServiceExn, TreatmentNotFoundExn{
        // TODO Auto-generated method stub
    	try {
			Provider provider = providerDAO.getProvider(npi);
			TreatmentExporter visitor = new TreatmentExporter();
			return provider.exportTreatment(tid, visitor);
		} catch (ProviderExn e) {
			throw new ProviderNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new ProviderServiceExn(e.toString());
		}
    }

    

	@Override
	public void addDrugTreatment(TreatmentDto dto) throws ProviderServiceExn {
		DrugTreatmentType drugType = dto.getDrugTreatment();
		Treatment t = treatmentFactory.createDrugTreatment(dto.getDiagnosis(), drugType.getName(), drugType.getDosage());
		treatmentDAO.addTreatment(t);
	}


	@Override
	public void addRadiologyTreatment(TreatmentDto dto) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		RadiologyType radioType = dto.getRadiology();
		Treatment t = treatmentFactory.createRadiologyTreatment(dto.getDiagnosis(), radioType.getDate());
		treatmentDAO.addTreatment(t);
	}


	@Override
	public void addSurgeryTreatment(TreatmentDto dto) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		SurgeryType surType = dto.getSurgery();
		Treatment t = treatmentFactory.createSurgeryTreatment(dto.getDiagnosis(), surType.getDate());
		treatmentDAO.addTreatment(t);
	}

}
