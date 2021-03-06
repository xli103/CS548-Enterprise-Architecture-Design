package edu.stevens.cs548.clinic.domain;

import edu.stevens.cs548.clinic.domain.Treatment;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: RadiologyTreatment
 *
 */
@Entity

public class RadiologyTreatment extends Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	 @ElementCollection 
     @CollectionTable(name="DATES")
	 @Temporal(TemporalType.DATE)
     List<Date> Dates;
	
	public List<Date> getDates() {
		return Dates;
	}

	public void setDates(List<Date> dates) {
		Dates = dates;
	}

	public <T> T export(ITreatmentExporter<T> visitor) {
		return visitor.exportRadiology(this.getId(), this.getDiagnosis(), this.Dates);
	}

	public RadiologyTreatment() {
		super();
		this.setTreatmentType("R");
	}
   
}
