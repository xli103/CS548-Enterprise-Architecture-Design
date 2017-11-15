package edu.stevens.cs548.clinic.domain;

import edu.stevens.cs548.clinic.domain.Treatment;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Radiology
 *
 */
@Entity
@DiscriminatorValue("RA")
public class Radiology extends Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private List<Date> dates;
	public Radiology() {
		super();
		this.setTreatmentType("RA");
	}
	public List<Date> getDates() {
		return dates;
	}
	public void setDates(List<Date> dates) {
		this.dates = dates;
	}
	@Override
	public <T> T export(ITreatmentExporter<T> visitor) {
		return visitor.exportRadiology(this.getId(),
				   this.getProvider(),
		   		   this.getDiagnosis(),
		   		   this.getDates());
	}
   
}
