package edu.stevens.cs548.clinic.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import edu.stevens.cs548.clinic.domain.Specialization;
import edu.stevens.cs548.clinic.domain.ITreatmentDAO.TreatmentExn;

/**
 * Entity implementation class for Entity: Provider
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(
		name="SearchProviderByProviderID",query="select p from Provider p where p.providerId = :pid"),
	@NamedQuery(
		name="CountProviderByProviderID",query="select count(p) from Provider p where p.providerId = :pid"),
	@NamedQuery(
		name = "RemoveAllProviders",query = "delete from Provider p")
})
@Table(name = "PROVIDER")
public class Provider implements Serializable {

	
	private static final long serialVersionUID = 1L;
 
	@Id
	@GeneratedValue
	private long id;
	
	private long providerId;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private Specialization specialization;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProviderId() {
		return providerId;
	}
	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Specialization getSpecialization() {
		return specialization;
	}
	public void setSpecialization(Specialization specialization) {
		this.specialization = specialization;
	}
	@OneToMany(orphanRemoval=true,cascade = CascadeType.REMOVE, mappedBy = "provider")
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
	
	public void setTreatmentDAO (ITreatmentDAO tdao) {
		this.treatmentDAO = tdao;
	}
	
	public long addTreatment (Treatment t) {
		// Persist treatment and set forward and backward links
		this.treatmentDAO.addTreatment(t);
		this.getTreatments().add(t);
		if (t.getProvider() != this) {
			t.setProvider(this);
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
		// Check that the exported treatment is a treatment for this provider.
		Treatment t = treatmentDAO.getTreatment(tid);
		if (t.getProvider() != this) {
			throw new TreatmentExn("Inappropriate treatment access: provider = " + id + ", treatment = " + tid);
		}
		return t.export(visitor);
	}
	public Provider() {
		super();
		treatments = new ArrayList<Treatment>();
	}
}
