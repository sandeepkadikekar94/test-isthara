package com.upliv.user_service.model.staff;

import com.upliv.user_service.model.AddressDetails;
import com.upliv.user_service.model.common.ContactDetails;

public class UpdateStaffBasic {

    private StaffBasic staffBasic;
    private UpdateStaffStatus staffStatus;

    public StaffBasic getStaffBasic() {
        return staffBasic;
    }

    public void setStaffBasic(StaffBasic staffBasic) {
        this.staffBasic = staffBasic;
    }

    public UpdateStaffStatus getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(UpdateStaffStatus staffStatus) {
        this.staffStatus = staffStatus;
    }
}
