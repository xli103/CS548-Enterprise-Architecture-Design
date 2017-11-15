package edu.stevens.cs548.clinic.service.web.soap;

import javax.ejb.EJB;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;

@WebService(
		endpointInterface = "edu.stevens.cs548.clinic.service.web.soap.IProviderWebService", 
		targetNamespace = "http://cs548.stevens.edu/clinic/service/web/soap/provider", 
		serviceName = "ProviderWebService", portName = "ProviderWebPort")

@SOAPBinding(
		style=SOAPBinding.Style.DOCUMENT,
		use=SOAPBinding.Use.LITERAL,
		parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public class ProviderWebService implements IProviderWebService {

	@EJB(beanName="ProviderServiceBean")
	IProviderServiceLocal service;
	
	@Override
	public long addProvider(ProviderDto dto) throws ProviderServiceExn {
		return service.addProvider(dto);
	}

	@Override
	public ProviderDto getProvider(long id) throws ProviderServiceExn {
		return service.getProvider(id);
	}

	@Override
	public ProviderDto getProviderByProId(long pid) throws ProviderServiceExn {
		return service.getProviderByProId(pid);
	}

	@Override
	public long addDrugTreatment(TreatmentDto t) throws ProviderServiceExn {
		return service.addDrugTreatment(t);
	}

	@Override
	public long addRadiology(TreatmentDto t) throws ProviderServiceExn {
		return service.addRadiology(t);
	}

	@Override
	public long addSurgery(TreatmentDto t) throws ProviderServiceExn {
		return service.addSurgery(t);
	}

	@Override
	public String siteInfo() {
		return service.siteInfo();
	}

}
