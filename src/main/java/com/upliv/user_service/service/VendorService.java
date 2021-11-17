package com.upliv.user_service.service;

import com.upliv.user_service.exceptions.ErrorCodes;
import com.upliv.user_service.exceptions.ErrorMessage;
import com.upliv.user_service.exceptions.ResourceNotFoundException;
import com.upliv.user_service.model.vendor.Vendor;
import com.upliv.user_service.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor createVendor(Vendor vendor) {
        Vendor temp = vendorRepository.findVendorByMobile(vendor.getCountryCode(), vendor.getMobile());
        if(temp == null) {
            vendor = vendorRepository.save(vendor);
            return vendor;
        } else {
            throw new ResourceNotFoundException(ErrorMessage.USER_ALREADY_EXIST, ErrorCodes.USER_ALREADY_EXIST);
        }
    }

    public List<Vendor> getVendors() {
        return vendorRepository.findAll();
    }
}
