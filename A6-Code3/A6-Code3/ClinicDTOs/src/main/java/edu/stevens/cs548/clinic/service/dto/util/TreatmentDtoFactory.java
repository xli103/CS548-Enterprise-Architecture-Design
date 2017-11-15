package edu.stevens.cs548.clinic.service.dto.util;

import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.domain.Radiology;
import edu.stevens.cs548.clinic.domain.Surgery;
import edu.stevens.cs548.clinic.service.dto.DrugTreatmentType;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.RadiologyType;
import edu.stevens.cs548.clinic.service.dto.SurgeryType;
import edu.stevens.cs548.clinic.service.dto.TreatmentDto;

public class TreatmentDtoFactory {
	
	ObjectFactory factory;
	
	public TreatmentDtoFactory() {
		factory = new ObjectFactory();
	}
	
	public TreatmentDto createTreatmentDto () {
		
		return factory.createTreatmentDto();
	}
	
	public TreatmentDto createTreatmentDto (DrugTreatment t) {
		/*
		 * TODO: Initialize the DTO from the drug treatment.
		 */
		TreatmentDto treatmentDto = factory.createTreatmentDto();
		treatmentDto.setDiagnosis(t.getDiagnosis());
		treatmentDto.setId(t.getId());
		treatmentDto.setPatient(t.getPatient().getPatientId());
		treatmentDto.setProvider(t.getProvider().getProviderId());
		DrugTreatmentType drugTreatmentType = factory.createDrugTreatmentType();
		drugTreatmentType.setName(t.getDrug());
		drugTreatmentType.setDosage(t.getDosage());
		treatmentDto.setDrugTreatmentType(drugTreatmentType);
		return treatmentDto;
	}
	
	/*
	 * TODO: Repeat for other treatments.
	 */
	public TreatmentDto createTreatmentDto (Radiology t) {
		/*
		 * TODO: Initialize the DTO from the radiology treatment.
		 */
		TreatmentDto treatmentDto = factory.createTreatmentDto();
		treatmentDto.setDiagnosis(t.getDiagnosis());
		treatmentDto.setId(t.getId());
		treatmentDto.setPatient(t.getPatient().getPatientId());
		treatmentDto.setProvider(t.getProvider().getProviderId());
		RadiologyType radiologyType = factory.createRadiologyType();
		radiologyType.getDate().addAll(t.getDates());
		treatmentDto.setRadiology(radiologyType);
		return treatmentDto;
	}
	
	public TreatmentDto createTreatmentDto (Surgery t) {
		/*
		 * TODO: Initialize the DTO from the surgery treatment.
		 */
		TreatmentDto treatmentDto = factory.createTreatmentDto();
		treatmentDto.setDiagnosis(t.getDiagnosis());
		treatmentDto.setId(t.getId());
		treatmentDto.setPatient(t.getPatient().getPatientId());
		treatmentDto.setProvider(t.getProvider().getProviderId());
		SurgeryType surgeryType = factory.createSurgeryType();
		surgeryType.setDate(t.getDate());
		treatmentDto.setSurgery(surgeryType);
		return treatmentDto;
	}
}
