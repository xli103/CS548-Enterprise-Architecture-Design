package edu.stevens.cs548.clinic.domain;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ProviderDAO implements IProviderDAO {
	
	private EntityManager em;
	private TreatmentDAO treatmentDAO;
	
	public ProviderDAO(EntityManager em) {
		this.em = em;
		this.treatmentDAO = new TreatmentDAO(em);
	}
	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(PatientDAO.class.getCanonicalName());

	@Override
	public long addProvider(Provider prov) throws ProviderExn {
		long npi = prov.getNpi();
		Query query = em.createNamedQuery("CountProviderByNpi").setParameter("pid", npi);
		Long numExisting = (Long) query.getSingleResult();
		if (numExisting < 1) {
			// add to database (and sync with database to generate primary key)
			// Don't forget to initialize the patient aggregate with a treatment DAO
			em.persist(prov);
			prov.setTreatmentDAO(this.treatmentDAO);
			return prov.getNpi();
		} else {
			throw new ProviderExn("Insertion: Provcider with npi (" + npi + ") already exists.");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Provider getProvider(long npi) throws ProviderExn {
		Query query = em.createNamedQuery("SearchProviderByNpi").setParameter("pid", npi);
		List<Provider> prov = query.getResultList();
		if(prov.size() > 1)
			throw new ProviderExn("Duplicate provider records: provider npi =" + npi);
		else if(prov.size() < 1)
			throw new ProviderExn("Provider not found : provider npi =" + npi);
		else {
			Provider p = prov.get(0);
			p.setTreatmentDAO(this.treatmentDAO);
			return p;
		}
	}

	@Override
	public void deleteProviders() {
		Query update = em.createNamedQuery("RemoveAllProviders");
		update.executeUpdate();
	}

}