package edu.stevens.cs548.clinic.service.web.soap;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import edu.stevens.cs548.clinic.service.dto.ProviderDTO;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.service.ejb.IProviderService.ProviderNotFoundExn;
import edu.stevens.cs548.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.service.ejb.IProviderService.TreatmentNotFoundExn;

@WebService(targetNamespace="http://www.example.org/clinic/wsdl/provider")
/*
 * Endpoint interface for the provider Web service
 */

public interface IProviderWebService {

	
	public long createProvider (String name, long npi, String specialization) throws ProviderServiceExn;
	
	public ProviderDTO getProvider(long npi) throws ProviderServiceExn;
	
	public ProviderDTO getProviderByNameSpec(String name, String spec) throws ProviderServiceExn;
	
	public void deleteProvider(String name, long npi) throws ProviderServiceExn;
	
	public long addDrugTreatment(long id, String diagnosis, String drug, float dosage) throws ProviderNotFoundExn;
	
	public long addRadiologyTreatment(long id, String diagnosis, List<Date> dates) throws ProviderNotFoundExn;
	
	public long addSurgeryTreatment(long id, String diagnosis, Date date) throws ProviderNotFoundExn;
	
	public TreatmentDto[] getTreatments(long id, long[] tids) throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn;
	
	public void deleteTreatment(long id, long tid) throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn;
	
	public String siteInfo();
	
}
