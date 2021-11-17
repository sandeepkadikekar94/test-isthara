package com.upliv.user_service.controller;

import com.upliv.user_service.constants.UserConstants;
import com.upliv.user_service.model.Response;
import com.upliv.user_service.service.MiscellaneousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/miscellaneous")
public class MiscellaneousController {

    @Autowired
    private MiscellaneousService miscellaneousService;

    @PostMapping(path = "/ist/version", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response addVersion(@RequestParam UserConstants.PLATFORM_TYPE platform , @RequestParam String version,
                               @RequestParam boolean forceupdate) {
        return new Response(miscellaneousService.addVersion(platform, version, forceupdate));
    }

    @GetMapping(path = "/ist/version", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response getAllVersions() {
        return new Response(miscellaneousService.getAllVersions());
    }
}
