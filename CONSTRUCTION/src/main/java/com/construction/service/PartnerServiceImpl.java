package com.construction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.construction.model.Partner;
import com.construction.repository.PartnerRepository;


@Service
public class PartnerServiceImpl  implements PartnerService{

	
    @Autowired
    private PartnerRepository partnerRepository;
	
	@Override
	public Partner savePartner(Partner partner) {
		   return partnerRepository.save(partner);
    }
	

	@Override
	public List<Partner> getAllPartners() {
		 return partnerRepository.findAll();
	}

	@Override
	public Partner getPartnerById(Long id) {
		 return partnerRepository.findById(id).orElse(null);
	}


	@Override
	public void deletePartnerById(Long id) {
		 partnerRepository.deleteById(id);
		
	}

}
