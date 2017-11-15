package edu.stevens.cs548.service.ejb;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;

import edu.stevens.cs548.clinic.service.dto.ProviderDTO;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.service.ejb.IPatientService.PatientNotFoundExn;
import edu.stevens.cs548.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.service.ejb.IPatientService.TreatmentNotFoundExn;

public interface IProviderService {
	
	public class ProviderServiceExn extends Exception{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ProviderServiceExn(String m){
			super(m);
		}
	}
	
	public class ProviderNotFoundExn extends ProviderServiceExn{
		private static final long serialVersionUID = 1L;
		public ProviderNotFoundExn(String m){
			super(m);
		}
	}
	
	public class TreatmentNotFoundExn extends ProviderServiceExn{
		private static final long serialVersionUID = 1L;
		public TreatmentNotFoundExn(String m){
			super(m);
		}
	}
	
	@WebMethod(operationName="create")
	public long createProvider (String name, long npi, String specialization) throws ProviderServiceExn;
	
	@WebMethod
	public ProviderDTO getProvider(long npi) throws ProviderServiceExn;
	
	@WebMethod
	public ProviderDTO getProviderByNameSpec(String name, String spec) throws ProviderServiceExn;
	
	@WebMethod
	public void deleteProvider(String name, long npi) throws ProviderServiceExn;
	
	@WebMethod
	public long addDrugTreatment(long id, String diagnosis, String drug, float dosage) throws ProviderNotFoundExn;
	
	@WebMethod
	public long addRadiologyTreatment(long id, String diagnosis, List<Date> dates) throws ProviderNotFoundExn;
	
	@WebMethod
	public long addSurgeryTreatment(long id, String diagnosis, Date date) throws ProviderNotFoundExn;
	
	@WebMethod
	public TreatmentDto[] getTreatments(long id, long[] tids) throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn;
	
	@WebMethod
	public void deleteTreatment(long id, long tid) throws ProviderNotFoundExn, TreatmentNotFoundExn, ProviderServiceExn;
	
	@WebMethod
	public String siteInfo();

}
