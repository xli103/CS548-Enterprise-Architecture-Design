package edu.stevens.cs548.clinic.domain;

import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;

/**
 * Entity implementation class for Entity: Patient
 *
 */
@Entity
@NamedQueries({
@NamedQuery(
		name="SearchPatientByNameDOB",
		query="select p from Patient p where p.name =:name and p.birthDate = :dob"),
@NamedQuery(
		name="SearchPatientByPatientID",
		query="select p from Patient p where p.patientId =:pid")
})
@Table(name="PATIENT")
public class Patient implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@PersistenceContext
	private EntityManager em;
	@Id
	@GeneratedValue
	private long id;
	private long patientId;
	private String name;
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public long getPatientId(){
		return patientId;
	}
	
	public void setPatientId(long patientId){
		this.patientId = patientId;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public Date getBirthDate(){
		return birthDate;
	}
	
	public void setBrithDate(Date birthDate){
		this.birthDate = birthDate;
	}
	

	@OneToMany(mappedBy = "patient", cascade = REMOVE)
	@OrderBy
	private List<Treatment> treatments;
	
	public List<Treatment> getTreatments(){
		return treatments;
	}
	
	public void setTreatments(List<Treatment> treatments){
		this.treatments = treatments;
	}
	
	@Transient
	private ITreatmentDAO treatmentDAO;
	
	public void setTreatmentDAO(ITreatmentDAO tdao){
		this.treatmentDAO = tdao;
	}
	
	public void addTreatment(Treatment t){
		this.treatmentDAO.addTreatment(t);
		this.getTreatments().add(t);
		if(t.getPatient() != this)
			t.setPatient(this);
	}
	
	public void addDrugTreatment(String diagnosis, String drug, float dosage){
		/*
		 * TODO: Add provider parameter when adding treatments to a patient!
		 */
		Drugtreatment treatment = new Drugtreatment();
		treatment.setDiagnosis(diagnosis);
		treatment.setDrug(drug);
		treatment.setDosage(dosage);
		this.addTreatment(treatment);
	}
	
	public void addSurgeryTreatment(String diagnosis, Date date){
		/*
		 * TODO: Add provider parameter when adding treatments to a patient!
		 */
		SurgeryTreatment treatment = new SurgeryTreatment();
		treatment.setDiagnosis(diagnosis);
		treatment.setDate(date);
		this.addTreatment(treatment);
	}
	
	public void addRadiologyTreatment(String diagnosis, List<Date> dates){
		/*
		 * TODO: Add provider parameter when adding treatments to a patient!
		 */
		RadiologyTreatment treatment = new RadiologyTreatment();
		treatment.setDiagnosis(diagnosis);
		treatment.setDates(dates);
		this.addTreatment(treatment);
	}
	
	public List<Long> getTreatmentIds(){
		List<Long> tids = new ArrayList<Long>();
		for(Treatment t : this.getTreatments()){
			tids.add(t.getId());
		}
		return tids;
	}
	
	public void visitTreatment(long tid, ITreatmentVisitor visitor) throws TreatmentExn {
			Treatment t = treatmentDAO.getTreatmentByDbId(tid);
			if(t.getPatient() == this){
				throw new TreatmentExn("Inappropriate treatment access: patient = " + id + ", treatment = " + tid );
			}
			t.visit(visitor);
	}
	
	public void deleteTreatment(long tid) throws TreatmentExn{
		Treatment t = treatmentDAO.getTreatmentByDbId(tid);
		if(t.getPatient() == this){
			throw new TreatmentExn("Inappropriate treatment access: patient = " + id + ", treatment = " + tid );
		}
		treatmentDAO.deleteTreatment(t);
	}
	
	public void visitTreatments(ITreatmentVisitor visitor){
		for (Treatment t : this.getTreatments()){
			t.visit(visitor);
		}
	}
	
	public Patient() {
		super();
		treatments = new ArrayList<Treatment>();
	}
   
}
