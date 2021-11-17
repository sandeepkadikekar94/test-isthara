package com.upliv.user_service.model.staff.attendance;

import com.upliv.user_service.constants.StaffConstants;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class Attendance {

    @Id
    @NotNull
    private UUID staffId;
    private List<DailyAttendance> dailyAttendance;
    private double attendancePercentage;
    private StaffConstants.EMPLOYMENT_STATUS status;
    private double totalWorkingDays;

    public UUID getStaffId() {

        setStatus(StaffConstants.EMPLOYMENT_STATUS.ACTIVE);
        return staffId;
    }

    public void setStaffId(UUID staffId) { this.staffId = staffId; }

    public List<DailyAttendance> getDailyAttendance() {
        return dailyAttendance;
    }

    public void setDailyAttendance(List<DailyAttendance> dailyAttendance) {
        this.dailyAttendance = dailyAttendance;
    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public StaffConstants.EMPLOYMENT_STATUS getStatus() {
        return status;
    }

    public void setStatus(StaffConstants.EMPLOYMENT_STATUS status) {
        this.status = status;
    }

    public double getTotalWorkingDays() {
        return totalWorkingDays;
    }

    public void setTotalWorkingDays(double totalWorkingDays) {
        this.totalWorkingDays = totalWorkingDays;
    }


    public Attendance updateAttendance(Attendance request){
        this.dailyAttendance = request.getDailyAttendance();
        this.attendancePercentage = request.getAttendancePercentage();
        this.status = request.getStatus();
        return this;
    }
}
