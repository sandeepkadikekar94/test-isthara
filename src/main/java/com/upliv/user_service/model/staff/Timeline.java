package com.upliv.user_service.model.staff;


import com.upliv.user_service.constants.StaffConstants;

import java.util.Date;
import java.util.UUID;

public class Timeline {

    private StaffConstants.timelineActions actionType;
    private String heading;
    private String description;
    private Date actionTimestamp;
    private UUID staffId;
    private UUID staffName;

    public StaffConstants.timelineActions getActionType() {
        return actionType;
    }

    public void setActionType(StaffConstants.timelineActions actionType) {
        this.actionType = actionType;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getActionTimestamp() {
        return actionTimestamp;
    }

    public void setActionTimestamp(Date actionTimestamp) {
        this.actionTimestamp = actionTimestamp;
    }

    public UUID getStaffId() {
        return staffId;
    }

    public void setStaffId(UUID staffId) {
        this.staffId = staffId;
    }

    public UUID getStaffName() {
        return staffName;
    }

    public void setStaffName(UUID staffName) {
        this.staffName = staffName;
    }
}
