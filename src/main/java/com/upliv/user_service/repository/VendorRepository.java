package com.upliv.user_service.repository;

import com.upliv.user_service.model.vendor.Vendor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.UUID;

public interface VendorRepository extends MongoRepository<Vendor, UUID> {
    @Query("{'countryCode': '?0', 'mobile':?1}")
    public abstract Vendor findVendorByMobile(String countryCode, String mobile);
}
