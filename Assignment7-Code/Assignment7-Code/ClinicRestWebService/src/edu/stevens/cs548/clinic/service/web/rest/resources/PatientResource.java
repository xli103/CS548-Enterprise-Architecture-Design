package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import edu.stevens.cs548.clinic.domain.IPatientDAO.PatientExn;
import edu.stevens.cs548.clinic.domain.Patient;
import edu.stevens.cs548.clinic.domain.PatientFactory;
import edu.stevens.cs548.clinic.domain.ProviderFactory;
import edu.stevens.cs548.clinic.domain.TreatmentFactory;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceLocal;
import edu.stevens.cs548.clinic.service.representations.PatientRepresentation;
import edu.stevens.cs548.clinic.service.representations.TreatmentRepresentation;

@Path("/patient")
@RequestScoped
public class PatientResource {
	
	final static Logger logger = Logger.getLogger(PatientResource.class.getCanonicalName());
	
	/*
	 * inject using HK2 (not CDI)
	 */
	
	//@Context
    //private UriInfo uriInfo;
    
    private PatientDtoFactory patientDtoFactory;
    private PatientFactory patientFactory = new PatientFactory();
	private TreatmentFactory treatmentFactory = new TreatmentFactory();
	private ProviderFactory providerFactory = new ProviderFactory();

    /**
     * Default constructor. 
     */
    public PatientResource() {
		/*
		 * TODO finish this
		 */
    }
    
	/*
	 *  inject using CDI
	 */
    @EJB(beanName="PatientServiceBean")
    private IPatientServiceLocal patientService;
    
    @GET
    @Path("site")
    @Produces("text/plain")
    public String getSiteInfo() {
    	return patientService.siteInfo();
    }

	/*
	 * input should be application/xml
	 */
    @POST
    @Consumes("application/xml")
    public Response addPatient(PatientRepresentation patientRep, @Context UriInfo uriInfo) {
    	try {
    		//Patient p = patientFactory.createPatient(patientRep.getPatientId(), patientRep.getName(), patientRep.getDob(), patientRep.getAge());
    		PatientDto dto = patientDtoFactory.createPatientDto();
    		dto.setPatientId(patientRep.getPatientId());
    		dto.setName(patientRep.getName());
    		dto.setDob(patientRep.getDob());
    	    dto.setAge(patientRep.getAge());
    		long id = patientService.addPatient(dto);
    		//patientService.addPatient(patientRep.getPatientId(), patientRep.getName(), patientRep.getDob(), patientRep.getAge());
    		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
    		URI url = ub.build(Long.toString(id));
    		return Response.created(url).build();
    	} catch (PatientServiceExn e) {
    		throw new WebApplicationException();
    	}
    }
    
	/**
	 * Query methods for patient resources.
	 */
	/*
	 *  output should be application/xml
	 */
    @GET
    @Path("{id}")
    @Produces("application/xml")
	public PatientRepresentation getPatient(@PathParam("id") String id, @Context UriInfo uriInfo) {
		try {
			long key = Long.parseLong(id);
			PatientDto patientDTO = patientService.getPatient(key);
			PatientRepresentation patientRep = new PatientRepresentation(patientDTO, uriInfo);
			return patientRep;
		} catch (PatientServiceExn e) {
			throw new WebApplicationException();
		}
	}
    
	/*
	 * output should be application/xml
	 */
    @GET
    @Path("patientId")
    @Produces("application/xml")
	public PatientRepresentation getPatientByPatientId(@QueryParam("patientId") String patientId, @Context UriInfo uriInfo) {
    	try{
    		long pid = Long.parseLong(patientId);
    		PatientDto patientDTO = patientService.getPatientByPatId(pid);
       		PatientRepresentation patientRep = new PatientRepresentation(patientDTO, uriInfo);
    		return patientRep;
    	}catch(PatientServiceExn e){
    		throw new WebApplicationException();
    	}
	}
    
	/*
	 *  output should be application/xml
	 */
    @GET
    @Path("{id}/patientTreatment/{tid}")
    @Produces("application/xml")
    public TreatmentRepresentation getPatientTreatment(@PathParam("id") String id, @PathParam("tid") String tid, @Context UriInfo uriInfo) {
    	try {
    		TreatmentDto treatment = patientService.getTreatment(Long.parseLong(id), Long.parseLong(tid)); 
    		TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, uriInfo);
    		return treatmentRep;
    	} catch (PatientServiceExn e) {
    		throw new WebApplicationException();
    	}
    }

}