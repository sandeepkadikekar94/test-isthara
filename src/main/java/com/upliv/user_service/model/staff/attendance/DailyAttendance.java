package com.upliv.user_service.model.staff.attendance;


import com.upliv.user_service.constants.StaffConstants;

import java.util.Date;

public class DailyAttendance {

    private StaffConstants.ATTENDANCE_FLAG attendanceFlag;
    private Date dateForAttendance;
    private StaffConstants.MONTHS_FOR_ATTENDANCE month;
    private int maxWorkingDays;
    private double attendancePercentage;

    public StaffConstants.ATTENDANCE_FLAG getAttendanceFlag() {
        return attendanceFlag;
    }

    public void setAttendanceFlag(StaffConstants.ATTENDANCE_FLAG attendanceFlag) {
        this.attendanceFlag = attendanceFlag;
    }

    public Date getDateForAttendance() {
        return dateForAttendance;
    }

    public void setDateForAttendance(Date dateForAttendance) {
        this.dateForAttendance = dateForAttendance;
    }

    public StaffConstants.MONTHS_FOR_ATTENDANCE getMonth(){
        return month;
    }

    public void setMonth(StaffConstants.MONTHS_FOR_ATTENDANCE month) {
        this.month = month;
    }

    //    public void setMonth(Date date){
//        String temp = date.toString();
//        LocalDate locDate = LocalDate.parse(temp);
//        Month localMonth = locDate.getMonth();
//        this.month = localMonth;
//    }

    public double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public int getMaxWorkingDays() {
        return maxWorkingDays;
    }

    public void setMaxWorkingDays(int maxWorkingDays) {
        this.maxWorkingDays = maxWorkingDays;
    }

    //
//    public void setMonth(Date date){
//        int tempMonth = date.getMonth();
//        String temp = getMonthForInt(tempMonth);
//        this.month = month;
//    }
//
//
//    String getMonthForInt(int m) {
//        String month = "Invalid";
//        DateFormatSymbols dfs = new DateFormatSymbols();
//        String[] months = dfs.getMonths();
//        if (m >= 0 && m <= 11 ) {
//            month = months[m];
//        }
//        return month;
//    }
}
