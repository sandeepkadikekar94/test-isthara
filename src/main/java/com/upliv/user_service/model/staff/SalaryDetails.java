package com.upliv.user_service.model.staff;

import java.util.Date;
import java.util.List;

public class SalaryDetails {

    private int advancePaid;
    private int monthlySalary;
    private Date paymentDate;
    private int otherAllowances;
    private List<PaymentDetails> paymentDetails;
    private BankDetails bankDetails;

    public int getAdvancePaid() {
        return advancePaid;
    }

    public void setAdvancePaid(int advancePaid) {
        this.advancePaid = advancePaid;
    }

    public int getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(int monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public int getOtherAllowances() {
        return otherAllowances;
    }

    public void setOtherAllowances(int otherAllowances) {
        this.otherAllowances = otherAllowances;
    }

    public List<PaymentDetails> getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(List<PaymentDetails> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }
}
