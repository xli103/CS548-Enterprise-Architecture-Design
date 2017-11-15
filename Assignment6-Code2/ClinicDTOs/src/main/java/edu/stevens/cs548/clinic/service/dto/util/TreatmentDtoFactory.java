package edu.stevens.cs548.clinic.service.dto.util;

import edu.stevens.cs548.clinic.domain.DrugTreatment;
import edu.stevens.cs548.clinic.domain.RadiologyTreatment;
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
	
	public TreatmentDto createDrugTreatmentDto () {
		// TODO
		return factory.createTreatmentDto();
	}
	
	public TreatmentDto createTreatmentDto (DrugTreatment t) {
		/*
		 *  Initialize the DTO from the drug treatment.
		 */
		TreatmentDto d = factory.createTreatmentDto();
		DrugTreatmentType drugInfo = factory.createDrugTreatmentType();
		drugInfo.setName(t.getDrug());
		drugInfo.setDosage(t.getDosage());
		d.setDrugTreatment(drugInfo);
		return d;
	}
	
	/*
	 *  Repeat for other treatments.
	 */

	public TreatmentDto createTreatmentDto (RadiologyTreatment t) {
		/*
		 *  Initialize the DTO from the drug treatment.
		 */
		TreatmentDto d = factory.createTreatmentDto();
		RadiologyType radioInfo = factory.createRadiologyType();
		d.setRadiology(radioInfo);
		return d;
	}
	
	public TreatmentDto createTreatmentDto (SurgeryType t) {
		/*
		 *  Initialize the DTO from the drug treatment.
		 */
		TreatmentDto d = factory.createTreatmentDto();
		SurgeryType surInfo = factory.createSurgeryType();
		surInfo.setDate(t.getDate());
		d.setSurgery(t);
		return d;
	}
}
