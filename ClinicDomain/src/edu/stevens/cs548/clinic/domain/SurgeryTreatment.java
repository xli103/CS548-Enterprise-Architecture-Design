package edu.stevens.cs548.clinic.domain;

import edu.stevens.cs548.clinic.domain.Treatment;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: SurgeryTreatment
 *
 */
@Entity
@DiscriminatorValue("S")

public class SurgeryTreatment extends Treatment implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Temporal(TemporalType.DATE)
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public SurgeryTreatment() {
		super();
		this.setTreatmentType("S");
	}

	@Override
	public void visit(ITreatmentVisitor visitor) {
		visitor.visitSurgery(this.getId(), this.getDiagnosis(), this.date);
	}
   
}
