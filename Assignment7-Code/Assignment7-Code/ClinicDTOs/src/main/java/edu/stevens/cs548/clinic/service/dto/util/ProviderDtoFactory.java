package edu.stevens.cs548.clinic.service.dto.util;

import edu.stevens.cs548.clinic.domain.Provider;
import edu.stevens.cs548.clinic.service.dto.ObjectFactory;
import edu.stevens.cs548.clinic.service.dto.ProviderDto;

public class ProviderDtoFactory {
		ObjectFactory factory;
		
		public ProviderDtoFactory() {
			factory = new ObjectFactory();
		}
		
		public ProviderDto createProviderDto () {
			return factory.createProviderDto();
		}
		
		public ProviderDto createProviderDto (Provider p) {
			ProviderDto d = factory.createProviderDto();
			/*
			 * TODO: Initialize the fields of the DTO.
			 */
			d.setId(p.getId());
			d.setName(p.getName());
			d.setProviderId(p.getProviderId());
			d.setSpecialization(p.getSpecialization().toString());
			d.getTreatments().addAll(p.getTreatmentIds());
			return d;
		}
}
