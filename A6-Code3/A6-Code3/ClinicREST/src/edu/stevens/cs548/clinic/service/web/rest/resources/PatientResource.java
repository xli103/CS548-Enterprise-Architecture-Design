package edu.stevens.cs548.clinic.service.web.rest.resources;

import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.PatientDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IPatientServiceRemote;
import edu.stevens.cs548.clinic.service.web.rest.PatientRepresentation;
import edu.stevens.cs548.clinic.service.web.rest.TreatmentRepresentation;

import java.net.URI;
import java.util.Date;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

@Path("/patient")
@RequestScoped
public class PatientResource {
    @SuppressWarnings("unused")
   // @Context
   // private UriInfo context;
    
    private PatientDtoFactory patientDtoFactory;
    
    /**
     * Default constructor. 
     */
    public PatientResource() {
        // TODO Auto-generated constructor stub
    }
    
    @EJB(beanName="PatientServiceBean")
    private IPatientServiceRemote patientService;
    
    

    /**
     * Retrieves representation of an instance of PatientResource
     * @return an instance of PatientRepresentation
     */
    @GET
    @Path("{id}")
    @Produces("application/xml")
    
    public PatientRepresentation getPatient(@PathParam("id") String id, @Context UriInfo context) {
    	try{
    		long key = Long.parseLong(id);
    		PatientDto patientDTO = patientService.getPatient(key);
       		PatientRepresentation patientRep = new PatientRepresentation(patientDTO, context);
    		return patientRep;
    	}catch(PatientServiceExn e){
    		throw new WebApplicationException();
    	}
    }
    
    @GET
    @Path("patientId")
    @Produces("application/xml")
    public PatientRepresentation getPatientByPatientId(@QueryParam("id") String patientId, @Context UriInfo context){
    	try{
    		long pid = Long.parseLong(patientId);
    		PatientDto patientDTO = patientService.getPatientByPatId(pid);
       		PatientRepresentation patientRep = new PatientRepresentation(patientDTO, context);
    		return patientRep;
    	}catch(PatientServiceExn e){
    		throw new WebApplicationException();
    	}
    }

    @GET
    @Produces("application/xml")
    public PatientRepresentation getPatientByNameDob(@QueryParam("name") String name,
    												 @QueryParam("name") String dob){
    	Date brithDate = DatatypeConverter.parseDate(dob).getTime();
		//PatientDto patientDTO = patientService
		//PatientRepresentation patientRep = new PatientRepresentation(patientDTO, context);
		return null;
    }
    
    @POST
    @Consumes("application/xml")
    public Response addPatient(PatientRepresentation patientRep, @Context UriInfo context){
    	try {
    		PatientDto dto = patientDtoFactory.createPatientDto();
    		dto.setPatientId(patientRep.getPatientId());
    		dto.setName(patientRep.getName());
    		dto.setDob(patientRep.getDob());
    		dto.setAge(patientRep.getAge());
    		long id = patientService.addPatient(dto);
    		UriBuilder ub = context.getAbsolutePathBuilder().path("{id}");
    		URI url = ub.build(Long.toString(id));
    		return Response.created(url).build();
    	} catch (PatientServiceExn e) {
    		throw new WebApplicationException();
    	}
    }
    
    public TreatmentRepresentation getPatientTreatment(@PathParam("id") String id, @PathParam("tid") String tid, @Context UriInfo context) {
    	try {
    		TreatmentDto treatment = patientService.getTreatment(Long.parseLong(id), Long.parseLong(tid)); 
    		TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, context);
    		return treatmentRep;
    	} catch (PatientServiceExn e) {
    		throw new WebApplicationException();
    	}
    }
   
}