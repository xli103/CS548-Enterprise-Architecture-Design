package edu.stevens.cs548.clinic.service.representations;

import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.web.rest.data.ProviderType;
import edu.stevens.cs548.clinic.service.web.rest.data.dap.LinkType;

@XmlRootElement
public class ProviderRepresentation extends ProviderType {
	
	public List<LinkType> getLinksTreatments() {
		return this.getTreatments();
	}
	
	public static LinkType getProviderLink(long id, UriInfo uriInfo) {
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		ub.path("provider").path("{id}");
		String providerURI = ub.build(Long.toString(id)).toString();

		LinkType link = new LinkType();
		link.setUrl(providerURI);
		link.setRelation(Representation.RELATION_PATIENT);
		link.setMediaType(Representation.MEDIA_TYPE);
		return link;
	}
	
	public ProviderRepresentation(){
		super();
	}
	
	public ProviderRepresentation(ProviderDto dto, UriInfo uriInfo){
		this.id = dto.getId();
		this.providerId = dto.getProviderId();
		this.name = dto.getName();
		this.specialization = dto.getSpecialization();
		
		List<Long> treatments = dto.getTreatments();
		List<LinkType> links = this.getTreatments();
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		ub.path("treatment");
		for(long t : treatments){
			LinkType link = new LinkType();
			UriBuilder ubTreatment = ub.clone().path("{tid}");
			String treatmentURI = ubTreatment.build(Long.toString(t)).toString();
			link.setUrl(treatmentURI);
			link.setRelation(Representation.RELATION_TREATMENT);
			link.setRelation(Representation.MEDIA_TYPE);
			links.add(link);
		}
	}
	

}
