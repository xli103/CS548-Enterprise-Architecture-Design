package edu.stevens.cs548.service.ejb;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.stevens.cs548.clinic.domain.IProviderDAO;
import edu.stevens.cs548.clinic.domain.IProviderDAO.ProviderExn;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.domain.ITreatmentVisitor;
import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.domain.ProviderDAO;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ProviderDTO;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

/**
 * Session Bean implementation class ProviderService1
 */
@Stateless
public class ProviderService implements IProviderService {

	private ProviderFactory providerFactory;
	
	private IProviderDAO providerDAO;
    /**
     * Default constructor. 
     */
	
    public ProviderService() {
        // TODO Auto-generated constructor stub
    	providerFactory = new ProviderFactory();
    }

	@PersistenceContext(unitName="ClinicDomain")
	private EntityManager em;
	
	@PostConstruct
	private void initialize(){
		providerDAO = new ProviderDAO(em);
	}

	private ProviderDTO providerToDTO(Provider provider){
		return new ProviderDTO(provider);
	}
    
	/**
     * @see IProviderService#getTreatments(long, long[])
     */
	
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
	
    public TreatmentDto[] getTreatments(long npi, long[] tids) 
    		throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn{
        // TODO Auto-generated method stub
		try{
			Provider provider = providerDAO.getProviderByNpi(npi);
			TreatmentDto[] treatments = new TreatmentDto[tids.length];
			TreatmentPDOtoDTO visitor = new TreatmentPDOtoDTO();
			for(int i=0; i<tids.length; i++){
				provider.visitTreatment(tids[i], visitor);
				treatments[i] = visitor.getDTO();
			}
			return treatments;
		}catch(ProviderExn e){
			throw new ProviderNotFoundExn(e.toString());
		}catch(TreatmentExn e){
			throw new ProviderServiceExn(e.toString());
		}
	}

	/**
     * @see IProviderService#getProvider(long)
     */
    public ProviderDTO getProvider(long npi) throws ProviderServiceExn {
        // TODO Auto-generated method stub
    	try{
    		Provider provider = providerDAO.getProviderByNpi(npi);
    		return new ProviderDTO(provider);
    	}catch(ProviderExn e){
    		throw new ProviderServiceExn(e.toString());
    	}
    }

	/**
     * @see IProviderService#addSurgeryTreatment(long, String, Date)
     */
    public long addSurgeryTreatment(long npi, String diagnosis, Date date) throws ProviderNotFoundExn {
        // TODO Auto-generated method stub
    	try{
			Provider provider = providerDAO.getProviderByNpi(npi);
			provider.addSurgeryTreatment(diagnosis, date);
			return provider.getTreatmentIds().get(0);
		}catch(ProviderExn e){
			throw new ProviderNotFoundExn(e.toString());
		}
    }

	/**
     * @see IProviderService#getProviderByNameSpec(String, String)
     */
    public ProviderDTO getProviderByNameSpec(String name, String spec) {
        // TODO Auto-generated method stub
			return null;
    }

	/**
     * @see IProviderService#deleteProvider(String, long)
     */
    public void deleteProvider(String name, long npi) throws ProviderServiceExn {
        // TODO Auto-generated method stub
    	try{
    		Provider provider = providerDAO.getProviderByNpi(npi);
    		if(!name.equals(provider.getName())) {
    			throw new ProviderServiceExn("Tried to delete wrong patient: name = " + name + ", id= " + npi);
    		}
    		else{
    			providerDAO.deleteProvider(provider);
    		}
    	}catch(ProviderExn e){
    		throw new ProviderServiceExn(e.toString());
    	}
    }

	/**
     * @see IProviderService#addRadiologyTreatment(long, String, List<Date>)
     */
    public long addRadiologyTreatment(long npi, String diagnosis, List<Date> dates) throws ProviderNotFoundExn {
        // TODO Auto-generated method stub
    	try{
			Provider provider = providerDAO.getProviderByNpi(npi);
			provider.addRadiologyTreatment(diagnosis, dates);
			return provider.getTreatmentIds().get(0);
		}catch(ProviderExn e){
			throw new ProviderNotFoundExn(e.toString());
		}
    }

	/**
     * @see IProviderService#deleteTreatment(long, long)
     */
    public void deleteTreatment(long npi, long tid) throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
        // TODO Auto-generated method stub
    	try{
			Provider provider = providerDAO.getProviderByNpi(npi);
			provider.deleteTreatment(tid);
		}catch(ProviderExn e){
			throw new ProviderNotFoundExn(e.toString());
		} catch (TreatmentExn e) {
			throw new TreatmentNotFoundExn(e.toString());
		} 	
    }

	/**
     * @see IProviderService#createProvider(String, long, String)
     */
    public long createProvider(String name, long npi, String specialization) throws ProviderServiceExn {
        // TODO Auto-generated method stub
    	Provider provider = this.providerFactory.createProvider(npi, name, specialization);
    	try{
    		providerDAO.addProvider(provider);
    	}catch(ProviderExn e){
    		throw new ProviderServiceExn(e.toString());
    	}
			return provider.getNpi();
    }

	/**
     * @see IProviderService#addDrugTreatment(long, String, String, float)
     */
    public long addDrugTreatment(long npi, String diagnosis, String drug, float dosage) throws ProviderNotFoundExn {
        // TODO Auto-generated method stub
    	try{
			Provider provider = providerDAO.getProviderByNpi(npi);
			provider.addDrugTreatment(diagnosis, drug, dosage);
			return provider.getTreatmentIds().get(0);
		}catch(ProviderExn e){
			throw new ProviderNotFoundExn(e.toString());
		}
    }

	/**
     * @see IProviderService#siteInfo()
     */
    
    @Resource(name="SiteInfo")
	private String siteInformation;
    
    public String siteInfo() {
        // TODO Auto-generated method stub
    	return siteInformation;
    }

}