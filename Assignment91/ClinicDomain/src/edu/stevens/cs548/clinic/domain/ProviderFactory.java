package edu.stevens.cs548.clinic.domain;

public class ProviderFactory implements IProviderFactory {

	@Override
	public Provider createProvider(long pid, String name, Specialization specialization) throws IProviderDAO.ProviderExn {
		Provider p = new Provider();
		p.setName(name);
		p.setProviderId(pid);
		p.setSpecialization(specialization);
		return p;
	}
}
