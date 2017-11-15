package edu.stevens.cs548.clinic.service.web.soap;

import javax.jws.WebService;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;

@WebService(
		endpointInterface = "edu.stevens.cs548.clinic.service.web.soap.IProviderWebService", 
		targetNamespace = "http://cs548.stevens.edu/clinic/service/web/soap", 
		serviceName = "ProviderWebService", portName = "ProviderWebPort")

public class ProviderWebService implements IProviderWebService {

	IProviderServiceLocal service;
	
	@Override
	public long addProvider(ProviderDto dto) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		return service.addProvider(dto);
	}

	@Override
	public ProviderDto getProviderByNpi(long npi) throws ProviderNotFoundExn {
		// TODO Auto-generated method stub
		return service.getProviderByNpi(npi);
	}

	@Override
	public ProviderDto getProvider(long id) throws ProviderNotFoundExn {
		// TODO Auto-generated method stub
		return service.getProvider(id);
	}

	@Override
	public void addDrugTreatment(TreatmentDto dto) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		service.addDrugTreatment(dto);
	}

	@Override
	public void addRadiologyTreatment(TreatmentDto dto) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		service.addRadiologyTreatment(dto);
	}

	@Override
	public void addSurgeryTreatment(TreatmentDto dto) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		service.addSurgeryTreatment(dto);
	}

	@Override
	public TreatmentDto getTreatment(long id, long tid) throws ProviderServiceExn {
		// TODO Auto-generated method stub
		return service.getTreatment(id, tid);
	}

}
