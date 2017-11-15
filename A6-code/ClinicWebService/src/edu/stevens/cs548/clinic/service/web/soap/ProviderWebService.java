package edu.stevens.cs548.clinic.service.web.soap;

import java.util.Date;


import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebService;

import edu.stevens.cs548.clinic.service.dto.ProviderDTO;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.service.ejb.IProviderService.ProviderNotFoundExn;
import edu.stevens.cs548.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.service.ejb.IProviderService.TreatmentNotFoundExn;
import edu.stevens.cs548.service.ejb.IProviderServiceRemote;

@WebService(
		endpointInterface="edu.stevens.cs548.clinic.service.web.soap.IProviderWebService",
		serviceName="ProviderWeb")

public class ProviderWebService implements IProviderWebService {

	@EJB(beanName="ProviderServiceBean")
	IProviderServiceRemote service;
	@Override
	public long createProvider(String name, long npi, String specialization) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		return service.createProvider(name, npi, specialization);
	}

	@Override
	public ProviderDTO getProvider(long npi) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		return service.getProvider(npi);
	}

	@Override
	public ProviderDTO getProviderByNameSpec(String name, String spec) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProvider(String name, long npi) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		service.deleteProvider(name, npi);
	}

	@Override
	public long addDrugTreatment(long id, String diagnosis, String drug, float dosage) throws ProviderNotFoundExn {
		// TODO Auto-generated method stub
		return service.addDrugTreatment(id, diagnosis, drug, dosage);
	}

	@Override
	public long addRadiologyTreatment(long id, String diagnosis, List<Date> dates) throws ProviderNotFoundExn {
		// TODO Auto-generated method stub
		return service.addRadiologyTreatment(id, diagnosis, dates);
	}

	@Override
	public long addSurgeryTreatment(long id, String diagnosis, Date date) throws ProviderNotFoundExn {
		// TODO Auto-generated method stub
		return service.addSurgeryTreatment(id, diagnosis, date);
	}

	@Override
	public TreatmentDto[] getTreatments(long id, long[] tids)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		// TODO Auto-generated method stub
		return service.getTreatments(id, tids);
	}

	@Override
	public void deleteTreatment(long id, long tid)
			throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn {
		// TODO Auto-generated method stub
		service.deleteTreatment(id, tid);
	}

	@Override
	public String siteInfo() {
		// TODO Auto-generated method stub
		return service.siteInfo();
	}

}
