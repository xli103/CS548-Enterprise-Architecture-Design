package edu.stevens.cs548.clinic.domain;

import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;

/**
 * Entity implementation class for Entity: Provider
 *
 */
@Entity
@NamedQuery(
		name="SearchProviderByProviderNPI",
		query="select p from Provider p where p.npi =:npi")
@Table(name = "PROVIDER")

public class Provider implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext
	private EntityManager em;
	@Id
	@GeneratedValue
	private long id;
	
	private long npi;
	private String name;
	private String specialization;
	
	public long getId(){
		return id;
	}
	
	public void setId(long id){
		this.id = id;
	}
	
	public long getNpi() {
		return npi;
	}

	public void setNpi(long npi) {
		this.npi = npi;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	@OneToMany(mappedBy = "provider", cascade = REMOVE)
	@OrderBy
	private List<Treatment> treatments;
	
	public List<Treatment> getTreatments() {
		return treatments;
	}

	public void setTreatments(List<Treatment> treatments) {
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
		if(t.getProvider() != this)
			t.setProvider(this);
	}
	
	public void addDrugTreatment(String diagnosis, String drug, float dosage){
		/*
		 * TODO: Add provider parameter when adding treatments to a patient!
		 */
		DrugTreatment treatment = new DrugTreatment();
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
			if(t.getProvider() == this){
				throw new TreatmentExn("Inappropriate treatment access: patient = " + npi + ", treatment = " + tid );
			}
			t.visit(visitor);
	}
	
	public void deleteTreatment(long tid) throws TreatmentExn{
		Treatment t = treatmentDAO.getTreatmentByDbId(tid);
		if(t.getProvider() == this){
			throw new TreatmentExn("Inappropriate treatment access: patient = " + npi + ", treatment = " + tid );
		}
		treatmentDAO.deleteTreatment(t);
	}
	
	public void visitTreatments(ITreatmentVisitor visitor){
		for (Treatment t : this.getTreatments()){
			t.visit(visitor);
		}
	}

	public Provider() {
		super();
		treatments = new ArrayList<Treatment>();
	}
   
}
