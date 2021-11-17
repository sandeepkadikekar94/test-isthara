package com.upliv.user_service.model;

public class ProfessionalDetails {

    private String companyName;
    private String designation;
    private String companyAddress;
    private String city;
    private String state;
    private String country;

    public String getCompanyName()
    {
        return  companyName;
    }
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    public String getDesignation()
    {
        return  designation;
    }
    public  void  setDesignation(String designation)
    {
        this.designation = designation;
    }
    public  String getCompanyAddress()
    {
        return  companyAddress;
    }
    public void setCompanyAddress(String companyAddress)
    {
        this.companyAddress = companyAddress;
    }
    public String getCity()
    {
        return  city;
    }
    public void setCity(String city)
    {
        this.city = city;
    }
    public String getState()
    {
        return  state;
    }
    public  void setState(String state)
    {
        this.state = state;
    }
    public String getCountry()
    {
        return country;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }

}
