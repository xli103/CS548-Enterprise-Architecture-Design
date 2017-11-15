package edu.stevens.cs548.clinic.service.web.rest;

import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import edu.stevens.cs548.clinic.service.dto.PatientDto;
import edu.stevens.cs548.clinic.service.web.rest.data.LinkType;
import edu.stevens.cs548.clinic.service.web.rest.data.PatientType;

@XmlRootElement
public class PatientRepresentation extends PatientType {
	
	public List<LinkType> getLinksTreatments() {
		return this.getTreatments();
	}
	
	public static LinkType getPatientLink(long id, UriInfo uriInfo) {
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		ub.path("patient").path("{id}");
		String patientURI = ub.build(Long.toString(id)).toString();

		LinkType link = new LinkType();
		link.setUrI(patientURI);
		link.setRelation(Representation.RELATION_PATIENT);
		link.setMediaType(Representation.MEDIA_TYPE);
		return link;
	}
	
	public PatientRepresentation(){
		super();
	}
	
	public PatientRepresentation(PatientDto dto, UriInfo uriInfo){
		this.id = dto.getId();
		this.patientId = dto.getPatientId();
		this.name = dto.getName();
		this.dob = dto.getDob();
		
		List<Long> treatments = dto.getTreatments();
		List<LinkType> links = this.getTreatments();
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		ub.path("treatment");
		for(long t : treatments){
			LinkType link = new LinkType();
			UriBuilder ubTreatment = ub.clone().path("{tid}");
			String treatmentURI = ubTreatment.build(Long.toString(t)).toString();
			link.setUrI(treatmentURI);
			link.setRelation(Representation.RELATION_TREATMENT);
			link.setRelation(Representation.MEDIA_TYPE);
			links.add(link);
		}
	}
}
