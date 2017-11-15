package edu.stevens.cs548.clinic.domain;

public class ProviderFactory implements IProviderFactory {

	@Override
	public Provider createProvider(long npi, String name, String specialization) {
		// TODO Auto-generated method stub
		Provider prov = new Provider();
		prov.setName(name);
		prov.setNpi(npi);
		prov.setSpecialization(specialization);
		return prov;
	}

}
