package com.construction.service;

import java.util.List;

import com.construction.model.Partner;

public interface PartnerService {

	
	
	 public Partner savePartner(Partner partner);
	 public List<Partner> getAllPartners();
	 public Partner getPartnerById(Long id);
	public void deletePartnerById(Long id);
	 
}
