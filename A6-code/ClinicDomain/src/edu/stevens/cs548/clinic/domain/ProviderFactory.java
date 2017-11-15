package edu.stevens.cs548.clinic.domain;


public class ProviderFactory implements IProviderFactory {

	@Override
	public Provider createProvider(long npi, String name, String specialization) {
		// TODO Auto-generated method stub
		Provider p = new Provider();
		p.setName(name);
		p.setNpi(npi);
		p.setSpecialization(specialization);
		return p;
	}

}