package edu.stevens.cs548.clinic.domain;

public interface IProviderFactory {
	public Provider createProvider(long pid, String name,Specialization specialization) throws IProviderDAO.ProviderExn;

}
