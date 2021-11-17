package com.upliv.user_service.controller;

import com.upliv.user_service.model.Response;
import com.upliv.user_service.model.vendor.Vendor;
import com.upliv.user_service.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response createVendor(@Valid @RequestBody Vendor vendor) {
        return new Response(vendorService.createVendor(vendor));
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAllVendors() {
        return new Response(vendorService.getVendors());
    }

//    @PutMapping(path = "/{staffId}/logout", produces = MediaType.APPLICATION_JSON_VALUE)
//    public Response logout(@PathVariable UUID staffId, @RequestBody DeviceInfo deviceInfo) {
//        staffService.logout(staffId, deviceInfo);
//        return new Response("logout success!");
//    }
}
