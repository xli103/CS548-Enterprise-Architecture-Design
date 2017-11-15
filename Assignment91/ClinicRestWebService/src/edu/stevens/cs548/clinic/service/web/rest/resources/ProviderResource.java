package edu.stevens.cs548.clinic.service.web.rest.resources;

import java.net.URI;
import java.util.logging.Logger;

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

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.ProviderDtoFactory;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.ejb.IProviderService.ProviderServiceExn;
import edu.stevens.cs548.clinic.service.ejb.IProviderServiceLocal;
import edu.stevens.cs548.clinic.service.representations.ProviderRepresentation;
import edu.stevens.cs548.clinic.service.representations.TreatmentRepresentation;

@Path("/provider")
@RequestScoped
public class ProviderResource {
	
	final static Logger logger = Logger.getLogger(ProviderResource.class.getCanonicalName());

   // @Context private UriInfo uriInfo;
	
    private ProviderDtoFactory providerDtoFactory;
    private TreatmentDtoFactory treatmentDtoFactory;
    /**
     * Default constructor. 
     */
    public ProviderResource() {
        // TODO Auto-generated constructor stub
    }
    
    @EJB(beanName="ProviderServiceBean")
    private IProviderServiceLocal providerService;
    
    
    @POST
    @Consumes("application/xml")
    public Response addProvider(ProviderRepresentation providerRep, @Context UriInfo uriInfo) {
    	try {
    		ProviderDto dto = providerDtoFactory.createProviderDto();
    		dto.setProviderId(providerRep.getProviderId());
    		dto.setName(providerRep.getName());
    		dto.setProviderId(providerRep.getProviderId());
    		long id = providerService.addProvider(dto);
    		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
    		URI url = ub.build(Long.toString(id));
    		return Response.created(url).build();
    	} catch (ProviderServiceExn e) {
    		throw new WebApplicationException();
    	}
    }
    

    /**
     * Retrieves representation of an instance of ProviderResource
     * @return an instance of ProviderRepresentation
     */
    @GET
    @Path("{id}")
    @Produces("application/xml")
    public ProviderRepresentation getProvider(@PathParam("id") String id, @Context UriInfo uriInfo) {
    		try {
    			long key = Long.parseLong(id);
    			ProviderDto providerDTO = providerService.getProvider(key);
    			ProviderRepresentation providerRep = new ProviderRepresentation(providerDTO, uriInfo);
    			return providerRep;
    		} catch (ProviderServiceExn e) {
    			throw new WebApplicationException();
    		}
    }
    
    @GET
    @Path("providerNPI")
    @Produces("application/xml")
	public ProviderRepresentation getProviderByNPI(@QueryParam("providerNPI") String providerNPI, @Context UriInfo uriInfo) {
    	try{
    		long npi = Long.parseLong(providerNPI);
    		ProviderDto providerDTO = providerService.getProviderByProId(npi);
       		ProviderRepresentation providerRep = new ProviderRepresentation(providerDTO, uriInfo);
    		return providerRep;
    	}catch(ProviderServiceExn e){
    		throw new WebApplicationException();
    	}
	}

    @GET
    @Path("{id}/providerTreatment/{tid}")
    @Produces("application/xml")
    public TreatmentRepresentation getProviderTreatment(@PathParam("id") String id, @PathParam("tid") String tid, @Context UriInfo uriInfo) {
    	try {
    		TreatmentDto treatment = providerService.getTreatment(Long.parseLong(id), Long.parseLong(tid)); 
    		TreatmentRepresentation treatmentRep = new TreatmentRepresentation(treatment, uriInfo);
    		return treatmentRep;
    	} catch (ProviderServiceExn e) {
    		throw new WebApplicationException();
    	}
    }
 /*
    @POST
    @Consumes("application/xml")
    public Response addTreatment(TreatmentRepresentation treatmentRep) {
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
    		UriBuilder ub = uriInfo.getAbsolutePathBuilder().path("{id}");
    		URI url = ub.build(Long.toString(id));
    		return Response.created(url).build();
    	}catch(ProviderServiceExn e){
    		throw new WebApplicationException();
    	}
    }
   */

}