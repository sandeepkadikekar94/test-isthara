package com.upliv.user_service.model.staff;

import com.upliv.user_service.model.common.CallLog;
import com.upliv.user_service.model.common.EmergencyContact;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Document
public class StaffDetails {
    @Id
    private UUID id;
    private PersonalDetails personalDetails;
    private EmergencyContact emergencyContactDetails;
    private StaffProfessionalDetails staffProfessionalDetails;
    private List<KycDetails> kycDetails;
    private List<CallLog> callLogs;
    private List<Timeline> actions = new LinkedList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public EmergencyContact getEmergencyContactDetails() {
        return emergencyContactDetails;
    }

    public void setEmergencyContactDetails(EmergencyContact emergencyContactDetails) {
        this.emergencyContactDetails = emergencyContactDetails;
    }

    public StaffProfessionalDetails getStaffProfessionalDetails() {
        return staffProfessionalDetails;
    }

    public void setStaffProfessionalDetails(StaffProfessionalDetails staffProfessionalDetails) {
        this.staffProfessionalDetails = staffProfessionalDetails;
    }

    public List<KycDetails> getKycDetails() {
        return kycDetails;
    }

    public void setKycDetails(List<KycDetails> kycDetails) {
        this.kycDetails = kycDetails;
    }

    public List<CallLog> getCallLogs() {
        return callLogs;
    }

    public void setCallLogs(List<CallLog> callLogs) {
        this.callLogs = callLogs;
    }

    public List<Timeline> getActions() {
        return actions;
    }

    public void setActions(List<Timeline> actions) {
        this.actions = actions;
    }

    public void addAction(Timeline action){
        this.actions.add(action);
    }

    public StaffDetails updateStaffDetails(StaffDetails ref){
        this.personalDetails = ref.getPersonalDetails();
        this.staffProfessionalDetails = ref.getStaffProfessionalDetails();
        this.emergencyContactDetails = ref.getEmergencyContactDetails();
        this.kycDetails = ref.getKycDetails();
        this.callLogs = ref.getCallLogs();
        return this;
    }
}
