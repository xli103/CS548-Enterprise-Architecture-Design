package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;

/**
 * Entity implementation class for Entity: Patient
 *
 */
/*
 * TODO
 */
@Entity
@NamedQueries({
	@NamedQuery(
		name="SearchPatientByPatientID",query="select p from Patient p where p.patientId = :pid"),
	@NamedQuery(
		name="CountPatientByPatientID",query="select count(p) from Patient p where p.patientId = :pid"),
	@NamedQuery(
		name = "RemoveAllPatients",query = "delete from Patient p")
})
@Table(name="PATIENT")
/*
 * TODO
 */
public class Patient implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// TODO JPA annotations
	@Id
	@GeneratedValue
	private long id;
	
	private long patientId;
	
	private String name;
	
	private int age;
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	// TODO JPA annotation
	@Temporal(TemporalType.DATE)
	private Date birthDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPatientId() {
		return patientId;
	}

	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	// TODO JPA annotations (propagate deletion of patient to treatments)
	@OneToMany(orphanRemoval=true, cascade=CascadeType.ALL, mappedBy = "patient")
	@OrderBy
	private List<Treatment> treatments;

	protected List<Treatment> getTreatments() {
		return treatments;
	}

	protected void setTreatments(List<Treatment> treatments) {
		this.treatments = treatments;
	}
	
	@Transient
	private ITreatmentDAO treatmentDAO;
	
	public void setTreatmentDAO (ITreatmentDAO tdao) {
		this.treatmentDAO = tdao;
	}
	
	public long addTreatment (Treatment t) {
		// Persist treatment and set forward and backward links
		this.treatmentDAO.addTreatment(t);
		this.getTreatments().add(t);
		if (t.getPatient() != this) {
			t.setPatient(this);
		}
		return t.getId();
	}
	
	public List<Long> getTreatmentIds() {
		List <Long> treatmentIds = new ArrayList<Long>();
		for (Treatment t : this.getTreatments()) {
			treatmentIds.add(t.getId());
		}
		return treatmentIds;
	}
	
	public <T> T exportTreatment(long tid, ITreatmentExporter<T> visitor) throws TreatmentExn {
		// Export a treatment without violated Aggregate pattern
		// Check that the exported treatment is a treatment for this patient.
		Treatment t = treatmentDAO.getTreatment(tid);
		if (t.getPatient() != this) {
			throw new TreatmentExn("Inappropriate treatment access: patient = " + id + ", treatment = " + tid);
		}
		return t.export(visitor);
	}
	
	public Patient() {
		super();
		/*
		 * TODO initialize lists
		 */
		treatments = new ArrayList<Treatment>();
	}
   
}
