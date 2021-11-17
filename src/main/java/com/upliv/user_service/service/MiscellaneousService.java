package com.upliv.user_service.service;

import com.upliv.user_service.constants.UserConstants;
import com.upliv.user_service.model.AppVersions;
import com.upliv.user_service.repository.AppVersionsRepository;
import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class MiscellaneousService {

    @Autowired
    AppVersionsRepository versionsRepository;

    @Autowired
    private ServiceInvoker serviceInvoker;

    public AppVersions addVersion(UserConstants.PLATFORM_TYPE platform, String version, boolean forceUpdate) {
        Optional<AppVersions> appVersions = versionsRepository.findById("Versions");
        AtomicReference<AppVersions> returnVal = new AtomicReference<>();
        appVersions.ifPresentOrElse((versions)->{
            if(platform == UserConstants.PLATFORM_TYPE.ANDROID) {
                if(!versions.getAndroidVersions().containsKey(version)) {
                    versions.getAndroidVersions().put(version, forceUpdate);
                }
            } else {
                if(!versions.getIosVersions().containsKey(version)) {
                    versions.getIosVersions().put(version, forceUpdate);
                }
            }
            returnVal.set(versionsRepository.save(versions));
        },()->{
            AppVersions newVersion = new AppVersions();
            newVersion.setAndroidVersions(new LinkedHashMap<>());
            newVersion.setIosVersions(new LinkedHashMap<>());

            if(platform == UserConstants.PLATFORM_TYPE.ANDROID) {
                newVersion.getAndroidVersions().put(version, forceUpdate);
            } else {
                newVersion.getIosVersions().put(version, forceUpdate);
            }
            returnVal.set(versionsRepository.save(newVersion));
        });
        return returnVal.get();
    }

    public List<AppVersions> getAllVersions() {
        return versionsRepository.findAll();
    }
}
