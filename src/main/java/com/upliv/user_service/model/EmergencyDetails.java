package com.upliv.user_service.model;

public class EmergencyDetails {

    private String emergencyContactPersonName;
    private String relationship;
    private String contactPhoneNo;
    private String contactAddress;
    private String city;
    private String state;
    private String country;

    public String getEmergencyContactPersonName()
    {
        return emergencyContactPersonName;
    }
    public void emergencyContactPersonName(String emergencyContactPersonName)
    {
        this.emergencyContactPersonName = emergencyContactPersonName;
    }
    public String getRelationship()
    {
        return  relationship;
    }
    public void setRelationship(String relationship)
    {
        this.relationship = relationship;
    }
    public String getContactPhoneNo()
    {
        return contactPhoneNo;
    }
    public void setContactPhoneNo(String contactPhoneNo)
    {
        this.contactPhoneNo = contactPhoneNo;
    }
    public String getContactAddress()
    {
        return  contactAddress;
    }
    public void  setContactAddress(String contactAddress)
    {
        this.contactAddress = contactAddress;
    }
    public String getCity()
    {
        return city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public String getState()
    {
        return state;
    }
    public  void  setState(String state)
    {
        this.state = state;
    }
    public String getCountry()
    {
        return country;
    }
    public void  setCountry(String country)
    {
        this.country = country;
    }
}
