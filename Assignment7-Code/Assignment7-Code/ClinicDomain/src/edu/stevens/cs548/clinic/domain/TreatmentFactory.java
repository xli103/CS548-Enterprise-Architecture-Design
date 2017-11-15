package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

public class TreatmentFactory implements ITreatmentFactory {

	@Override
	public Treatment createDrugTreatment( String drug, float dosage) {
		DrugTreatment treatment = new DrugTreatment();
		treatment.setDrug(drug);
		treatment.setDosage(dosage);
		treatment.setTreatmentType(TreatmentType.DRUG_TREATMENT.getTag());
		return treatment;
	}

	@Override
	public Treatment createRadiology( List<Date> dates) {
		// TODO Auto-generated method stub
		Radiology radiology = new Radiology();
		radiology.setDates(dates);
		radiology.setTreatmentType(TreatmentType.RADIOLOGY.getTag());
		return radiology;
	}

	@Override
	public Treatment createSurgery( Date date) {
		// TODO Auto-generated method stub
		Surgery surgery = new Surgery();
		surgery.setDate(date);
		surgery.setTreatmentType(TreatmentType.SURGERY.getTag());
		return surgery;
	}

}
