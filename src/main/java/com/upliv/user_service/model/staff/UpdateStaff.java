package com.upliv.user_service.model.staff;

public class UpdateStaff {

    private Staff staff;
    private UpdateStaffStatus staffStatus;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public UpdateStaffStatus getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(UpdateStaffStatus status) {
        this.staffStatus = status;
    }
}
