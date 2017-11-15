package edu.stevens.cs548.clinic.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class ProviderDAO implements IProviderDAO {
	
	private EntityManager em;
	private TreatmentDAO treatmentDAO;

	@Override
	public Provider getProviderByNpi(long npi) throws ProviderExn {
		// TODO Auto-generated method stub
		Provider p = em.find(Provider.class, npi);
		if(p == null){
			throw new ProviderExn("Provider not found: NPI = " + npi);
		}
		else{
			p.setTreatmentDAO(this.treatmentDAO);
			return p;
		}
	}

	@Override
	public void addProvider(Provider prov) throws ProviderExn {
		// TODO Auto-generated method stub
		long npi = prov.getNpi();
		TypedQuery<Provider> query = em.createNamedQuery(
				"SearchProviderByNPI", Provider.class).setParameter("npi", npi);
		Provider provider = query.getResultList().get(0);
		if(provider == null){
			em.persist(prov);
			prov.setTreatmentDAO(this.treatmentDAO);
		} 
		else{
			throw new ProviderExn("Provider already exist");
		}
	}

	@Override
	public void deleteProvider(Provider prov) throws ProviderExn {
		// TODO Auto-generated method stub
		em.remove(prov);
	}

	public ProviderDAO(EntityManager em){
		this.em = em;
		this.treatmentDAO = new TreatmentDAO(em);
	}

	@Override
	public Provider getProviderById(long id) throws ProviderExn {
		// TODO Auto-generated method stub
		
		return null;
	}

}
