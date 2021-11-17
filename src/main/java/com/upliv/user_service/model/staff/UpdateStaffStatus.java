package com.upliv.user_service.model.staff;


import com.upliv.user_service.constants.StaffConstants;

public class UpdateStaffStatus {
    private StaffConstants.timelineActions status;
    private String remarks;

    public StaffConstants.timelineActions getStatus(){
        return status;
    }

    public void setStatus(StaffConstants.timelineActions status){
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
