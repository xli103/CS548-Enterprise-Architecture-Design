package edu.stevens.cs548.clinic.service.web.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderNotFoundExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;

@WebService(
		name="IProviderWebPort",
		targetNamespace="http://cs548.stevens.edu/clinic/service/web/soap")
	        
	@SOAPBinding(
			style=SOAPBinding.Style.DOCUMENT,
			use=SOAPBinding.Use.LITERAL,
			parameterStyle=SOAPBinding.ParameterStyle.WRAPPED)

public interface IProviderWebService {
	
	@WebMethod
	public long addProvider(
			@WebParam(name="provider-dto",
	          targetNamespace="http://cs548.stevens.edu/clinic/dto")
			ProviderDto dto) throws ProviderServiceExn;
	
	@WebMethod
	@WebResult(name="provider-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public ProviderDto getProviderByNpi(long npi) throws ProviderNotFoundExn;
	
	@WebMethod
	@WebResult(name="provider-dto",
			   targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public ProviderDto getProvider(long id) throws ProviderNotFoundExn;
	
	@WebMethod(operationName="providerAddDrugTreatment")
	@WebResult(name="treatment-dto",
	           targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public void addDrugTreatment(TreatmentDto dto) throws ProviderServiceExn;
	
	@WebMethod(operationName="providerAddRadiologyTreatment")
	@WebResult(name="treatment-dto",
	           targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public void addRadiologyTreatment(TreatmentDto dto) throws ProviderServiceExn;
	
	@WebMethod(operationName="providerAddSurgeryTreatment")
	@WebResult(name="treatment-dto",
	           targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public void addSurgeryTreatment(TreatmentDto dto) throws ProviderServiceExn;
	
	@WebMethod(operationName="providerGetTreatment")
	@WebResult(name="treatment-dto",
	           targetNamespace="http://cs548.stevens.edu/clinic/dto")
	public TreatmentDto getTreatment(long id, long tid) throws ProviderServiceExn;

}
