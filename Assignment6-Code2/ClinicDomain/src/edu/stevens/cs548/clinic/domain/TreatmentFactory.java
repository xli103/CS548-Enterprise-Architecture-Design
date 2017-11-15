package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

public class TreatmentFactory implements ITreatmentFactory {

	@Override
	public Treatment createDrugTreatment(String diagnosis, String drug, float dosage) {
		// TODO Auto-generated method stub
		DrugTreatment treatment = new DrugTreatment();
		treatment.setTreatmentType(TreatmentType.DRUG_TREATMENT.getTag());
		treatment.setDiagnosis(diagnosis);
		treatment.setDrug(drug);
		treatment.setDosage(dosage);
		return treatment;
	}

	@Override
	public Treatment createRadiologyTreatment(String diagnosis, List<Date> dates) {
		// TODO Auto-generated method stub
		RadiologyTreatment t = new RadiologyTreatment();
		t.setDiagnosis(diagnosis);
		t.setDates(dates);
		t.setTreatmentType(TreatmentType.RADIOLOGY.getTag());
		return t;
	}

	@Override
	public Treatment createSurgeryTreatment(String diagnosis, Date dates) {
		// TODO Auto-generated method stub
		SurgeryTreatment t = new SurgeryTreatment();
		t.setDate(dates);
		t.setDiagnosis(diagnosis);
		t.setTreatmentType(TreatmentType.SURGERY.getTag());
		return t;
	}

}
