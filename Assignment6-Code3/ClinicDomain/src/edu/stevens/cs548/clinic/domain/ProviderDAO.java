package edu.stevens.cs548.clinic.domain;

import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class ProviderDAO implements IProviderDAO {
	
	private EntityManager em;
	private TreatmentDAO treatmentDAO;
	public ProviderDAO(EntityManager em){
		this.em = em;
		this.treatmentDAO = new TreatmentDAO(em);
	}
	
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(ProviderDAO.class.getCanonicalName());
	
	@Override
	public long addProvider(Provider provider) throws ProviderExn {
		// TODO Auto-generated method stub
		long pid = provider.getProviderId();
		Query query = em.createNamedQuery("CountProviderByProviderID").setParameter("pid", pid);
		Long numExisting = (Long) query.getSingleResult();
		if (numExisting < 1) {
			// TODO add to database (and sync with database to generate primary key)
			// Don't forget to initialize the patient aggregate with a treatment DAO
			em.persist(provider);
			provider.setTreatmentDAO(this.treatmentDAO);
			return provider.getId();
			
		} else {
			throw new ProviderExn("Insertion: Patient with patient id (" + pid + ") already exists.");
		}
		
	}

	@Override
	public Provider getProviderByProviderId(long pid) throws ProviderExn {
		// TODO Auto-generated method stub
		TypedQuery<Provider> query = 
				em.createNamedQuery("SearchProviderByProviderID", Provider.class)
				.setParameter("pid", pid);
		List<Provider> providers = query.getResultList();
		if(providers.size()>1)
			throw new ProviderExn("Duplicate patient records: patient id =" + pid );
		else if (providers.size()<1)
			throw new ProviderExn("Patient not found: patient id = " +pid);
		else
			providers.get(0).setTreatmentDAO(this.treatmentDAO);
			return providers.get(0);
	}

	@Override
	public Provider getProvider(long id) throws ProviderExn {
		// TODO Auto-generated method stub
		Provider p = em.find(Provider.class, id);
		if (p == null){
			throw new ProviderExn("Patient not found: primary key = "+ id);
		}else{
			p.setTreatmentDAO(this.treatmentDAO);
			return p;
		}
	}

	@Override
	public void deleteProviders() {
		// TODO Auto-generated method stub
		Query update = em.createNamedQuery("RemoveAllProviders");
		update.executeUpdate();
	}

}
