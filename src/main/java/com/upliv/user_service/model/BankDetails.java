package com.upliv.user_service.model;

public class BankDetails {
    private String bankAccountHolderName;
    private String bankName;
    private String bankAccountNumber;
    private String IFSCCode;

    public String getBankName()
    {
        return  bankName;
    }
    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }
    public String getBankAccountNumber()
    {
        return  bankAccountNumber;
    }
    public void setBankAccountNumber(String bankAccountNumber)
    {
        this.bankAccountNumber = bankAccountNumber;
    }public String getBankAccountHolderName()
    {
        return  bankAccountHolderName;
    }
    public void setBankAccountHolderName(String bankAccountHolderName)
    {
        this.bankAccountHolderName = bankAccountHolderName;
    }public String getIFSCCode()
    {
        return  IFSCCode;
    }
    public void setIFSCCode(String IFSCCode)
    {
        this.IFSCCode = IFSCCode;
    }

}
