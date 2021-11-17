package com.upliv.user_service.model;

import com.upliv.user_service.constants.UserConstants;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document
public class User {
    @Id
    private UUID userId;
    @NotNull
    private String fullName;
    @NotNull
    private String countryCode;
    @NotNull
    private String mobile;
    private String email;
    private String profilePicture;
    private boolean isActive;
    private boolean isResident;
    private boolean isVerified;
    private List<DeviceInfo> deviceInfos;
    private UserConstants.Gender gender;

    private Date createdTimeStamp;
    private Date updatedTimeStamp;
    /*Added by praba*/
    private Date dateOfBirth;
    private String permanentAddress;
    private String city;
    private String state;
    private String country;
    /*End */
    public User() {
        userId = UuidUtil.getTimeBasedUuid();
        createdTimeStamp = new Date();
        updatedTimeStamp = new Date();
        isActive = true;
        isVerified = false;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isResident() {
        return isResident;
    }

    public void setResident(boolean resident) {
        isResident = resident;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public Date getCreatedTimeStamp() {
        return createdTimeStamp;
    }

    public void setCreatedTimeStamp(Date createdTimeStamp) {
        this.createdTimeStamp = createdTimeStamp;
    }

    public Date getUpdatedTimeStamp() {
        return updatedTimeStamp;
    }

    public void setUpdatedTimeStamp(Date updatedTimeStamp) {
        this.updatedTimeStamp = updatedTimeStamp;
    }

    public UserConstants.Gender getGender() {
        return gender;
    }

    public void setGender(UserConstants.Gender gender) {
        this.gender = gender;
    }

    public List<DeviceInfo> getDeviceInfos() {
        return deviceInfos;
    }

    public void setDeviceInfos(List<DeviceInfo> deviceInfos) {
        this.deviceInfos = deviceInfos;
    }
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getPermanentAddress() {
        return permanentAddress;
    }
    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }
    public  String getCity()
    {
        return  city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public  String getState()
    {
        return  state;
    }
    public void setState(String state)
    {
        this.state = state;
    }
    public String getCountry()
    {
        return  country;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }
    public void addDeviceInfo(DeviceInfo deviceInfo) {
        if(deviceInfo != null) {
            if(deviceInfos != null) {
                for (int i = 0; i < deviceInfos.size(); i++) {
                    if (deviceInfos.get(i).getDeviceId().equals(deviceInfo.getDeviceId())) {
                        deviceInfos.remove(i);
                        break;
                    }
                }
            } else {
                deviceInfos = new ArrayList<>();
            }
            deviceInfos.add(deviceInfo);
        }
    }

    public void removeDeviceInfo(DeviceInfo deviceInfo) {
        if (deviceInfo != null && deviceInfos != null) {
            for (int i = 0; i < deviceInfos.size(); i++) {
                if (deviceInfos.get(i).getDeviceId().equals(deviceInfo.getDeviceId())) {
                    deviceInfos.remove(i);
                    break;
                }
            }
        }
    }

    public void updateUser(User newUser) {
        this.fullName = newUser.fullName !=null ? newUser.fullName :this.fullName;
        this.countryCode = newUser.countryCode !=null ? newUser.countryCode :this.countryCode;
        this.mobile = newUser.mobile !=null ? newUser.mobile : this.mobile;
        this.email = newUser.email !=null ? newUser.email: this.email;
        this.profilePicture = newUser.profilePicture !=null ? newUser.profilePicture : this.profilePicture;
        this.gender = newUser.gender !=null ? newUser.gender : this.gender;
        this.dateOfBirth = newUser.dateOfBirth != null ? newUser.dateOfBirth : this.dateOfBirth;
        this.city = newUser.city !=null ? newUser.city : this.city;
        this.country = newUser.country !=null ? newUser.country : this.country;
        this.state = newUser.state !=null ? newUser.state : this.state;
        this.permanentAddress = newUser.permanentAddress !=null ? newUser.permanentAddress : this.permanentAddress;
        this.updatedTimeStamp = new Date();
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", isActive=" + isActive +
                ", isResident=" + isResident +
                ", isVerified=" + isVerified +
                ", deviceInfos=" + deviceInfos +
                ", gender=" + gender +
                ", createdTimeStamp=" + createdTimeStamp +
                ", updatedTimeStamp=" + updatedTimeStamp +
                ", dateOfBirth=" + dateOfBirth +
                ", permanentAddress='" + permanentAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
