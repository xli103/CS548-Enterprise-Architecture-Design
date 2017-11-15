package edu.stevens.cs548.clinic.service.web.rest;

import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlRootElement;

import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;
import edu.stevens.cs548.clinic.service.dto.util.TreatmentDtoFactory;
import edu.stevens.cs548.clinic.service.web.rest.data.LinkType;
import edu.stevens.cs548.clinic.service.web.rest.data.TreatmentType;

@XmlRootElement
public class TreatmentRepresentation extends TreatmentType {
	
	public LinkType getLinkPatient() {
		return this.getPatient();
	}

	public LinkType getLinkProvider() {
		return this.getProvider();
	}

	public static LinkType getTreatmentLink(long tid, UriInfo uriInfo) {
		UriBuilder ub = uriInfo.getBaseUriBuilder();
		ub.path("treatment");
		UriBuilder ubTreatment = ub.clone().path("{tid}");
		String treatmentURI = ubTreatment.build(Long.toString(tid)).toString();
	
		LinkType link = new LinkType();
		link.setUrI(treatmentURI);
		link.setRelation(Representation.RELATION_TREATMENT);
		link.setMediaType(Representation.MEDIA_TYPE);
		return link;
	}
	
	private TreatmentDtoFactory treatmentDtoFactory;
	
	public TreatmentRepresentation() {
		super();
		treatmentDtoFactory = new TreatmentDtoFactory();
	}
	
	public TreatmentRepresentation (TreatmentDto dto, UriInfo uriInfo) {
		this();
		this.id = getTreatmentLink(dto.getId(), uriInfo);
		this.patient =  PatientRepresentation.getPatientLink(dto.getPatient(), uriInfo);
		/*
		 * TODO: Need to fill in provider information.
		 */
		this.provider = ProviderRepresentation.getProviderLink(dto.getProvider(), uriInfo);
		this.diagnosis = dto.getDiagnosis();
		if (dto.getDrugTreatmentType() != null) {
			this.drugTreatment.setDosage(dto.getDrugTreatmentType().getDosage()); 
			this.drugTreatment.setName(dto.getDrugTreatmentType().getName());
		} else if (dto.getSurgery() != null) {
			this.surgery.setDate(dto.getSurgery().getDate());
		} else if (dto.getRadiology() != null) {
			
		}
	}

	public TreatmentDto getTreatment() {
		TreatmentDto m = null;
		
		if (this.getDrugTreatment() != null) {
			m = treatmentDtoFactory.createTreatmentDto();
			m.setId(Representation.getId(id));
			m.setPatient(Representation.getId(patient));
			m.setProvider(Representation.getId(provider));
			m.setDiagnosis(diagnosis);
			DrugTreatmentType t = new DrugTreatmentType();
			t.setDosage(this.drugTreatment.getDosage());
			t.setName(this.drugTreatment.getName());
			m.setDrugTreatmentType(t);
		} else if (this.getSurgery() != null) {
			m = treatmentDtoFactory.createTreatmentDto();
			m.setId(Representation.getId(id));
			m.setPatient(Representation.getId(patient));
			m.setProvider(Representation.getId(provider));
			m.setDiagnosis(diagnosis);
			SurgeryType s = new SurgeryType();
			s.setDate(this.getSurgery().getDate());
			m.setSurgery(s);
			
		} else if (this.getRadiology() != null) {
			m = treatmentDtoFactory.createTreatmentDto();
			m.setId(Representation.getId(id));
			m.setPatient(Representation.getId(patient));
			m.setProvider(Representation.getId(provider));
			m.setDiagnosis(diagnosis);
			RadiologyType r = new RadiologyType();
			m.setRadiology(r);
		}
		return m;
	}

}
