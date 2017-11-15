package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
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

import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;
import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceRemote;
import edu.stevens.cs548.clinic.service.ejb.IPatientService.PatientServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.web.rest.PatientRepresentation;
import edu.stevens.cs548.clinic.service.web.rest.ProviderRepresentation;
import edu.stevens.cs548.clinic.service.web.rest.TreatmentRepresentation;
import edu.stevens.cs548.clinic.service.web.rest.data.ProviderType;

@Path("/provider")
@RequestScoped
public class ProviderResource {
    @SuppressWarnings("unused")
    @Context
    private UriInfo context;
    
    private ProviderDtoFactory providerDtoFactory;
    private TreatmentDtoFactory treatmentDtoFactory;
    /**
     * Default constructor. 
     */
    public ProviderResource() {
        // TODO Auto-generated constructor stub
    }
    
    @EJB(beanName="ProviderServiceBean")
    private IProviderServiceRemote providerService;

    /**
     * Retrieves representation of an instance of ProviderResource
     * @return an instance of ProviderType
     */
    @GET
    @Path("{id}")
    @Produces("application/xml")
    public ProviderRepresentation getProvider(@PathParam("id") String id) {
    	try{
    		long key = Long.parseLong(id);
    		ProviderDto providerDTO = providerService.getProvider(key);
       		ProviderRepresentation providerRep = new ProviderRepresentation(providerDTO, context);
    		return providerRep;
    	}catch(ProviderServiceExn e){
    		throw new WebApplicationException();
    	}
    }
    
    @GET
    @Path("providerId")
    @Produces("application/xml")
    public ProviderRepresentation getProviderByPatientId(@QueryParam("id") String providerId){
    	try{
    		long pid = Long.parseLong(providerId);
    		ProviderDto providerDTO = providerService.getProviderByProId(pid);
       		ProviderRepresentation providerRep = new ProviderRepresentation(providerDTO, context);
    		return providerRep;
    	}catch(ProviderServiceExn e){
    		throw new WebApplicationException();
    	}
    }
    
    @POST
    @Consumes("application/xml")
    public Response addProvider(ProviderRepresentation providerRep){
    	try {
    		ProviderDto dto = providerDtoFactory.createProviderDto();
    		dto.setId(providerRep.getId());
    		dto.setName(providerRep.getName());
    		dto.setProviderId(providerRep.getProviderId());
    		dto.setSpecialization(providerRep.getSpecialization());
    		long id = providerService.addProvider(dto);
    		UriBuilder ub = context.getAbsolutePathBuilder().path("{id}");
    		URI url = ub.build(Long.toString(id));
    		return Response.created(url).build();
    	} catch (ProviderServiceExn e) {
    		throw new WebApplicationException();
    	}
    }
    
    public TreatmentRepresentation getProviderTreatment(@PathParam("id") String id, @PathParam("tid") String tid) {
    	try {
    		TreatmentDto treatment = providerService.getTreatment(Long.parseLong(id), Long.parseLong(tid));
    		TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, context);
    		return treatmentRep;
    	} catch (ProviderServiceExn e) {
    		throw new WebApplicationException();
    	}
    }
    
    public Response addTreatment(TreatmentRepresentation treatmentRep){
    	try{
    		TreatmentDto dto = treatmentDtoFactory.createTreatmentDto();
    		dto = treatmentRep.getTreatment();
    		long id = 0;
    		if(dto.getDrugTreatmentType() != null){
    			id = providerService.addDrugTreatment(dto);
    		}
    		else if(dto.getSurgery() != null){
    			id = providerService.addSurgery(dto);
    		}
    		else if(dto.getRadiology() != null){
    			id = providerService.addRadiology(dto);
    		}
    		UriBuilder ub = context.getAbsolutePathBuilder().path("{id}");
    		URI url = ub.build(Long.toString(id));
    		return Response.created(url).build();
    	}catch(ProviderServiceExn e){
    		throw new WebApplicationException();
    	}
    }
    

}