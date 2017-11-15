package edu.stevens.cs548.clinic.domain;

import java.util.Date;
import java.util.List;

public class TreatmentExporter implements ITreatmentExporter<Treatment> {
	TreatmentFactory tf = new TreatmentFactory();
	@Override
	public Treatment exportDrugTreatment(long tid, Provider p, String diagnosis, String drug, float dosage) {
		// TODO Auto-generated method stub
		DrugTreatment dt =(DrugTreatment) tf.createDrugTreatment( drug, dosage);
		dt.setId(tid);
		dt.setProvider(p);
		return dt;
	}

	@Override
	public Treatment exportRadiology(long tid, Provider p, String diagnosis, List<Date> dates) {
		// TODO Auto-generated method stub
		Radiology ra = (Radiology) tf.createRadiology( dates);
		ra.setId(tid);
		ra.setProvider(p);
		return ra;
	}

	@Override
	public Treatment exportSurgery(long tid, Provider p, String diagnosis, Date date) {
		// TODO Auto-generated method stub
		Surgery su = (Surgery) tf.createSurgery( date);
		su.setId(tid);
		su.setProvider(p);
		return su;
	}

}
