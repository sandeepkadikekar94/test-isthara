package com.upliv.user_service.model.common;

import java.util.Date;
import java.util.List;

public class CallLog {
    private List<String> callResponse;
    private Date scheduledTimestamp;
    private  String reason;
    private String booked_with;
    private String bookingreason;

    public String getBooked_with() {
        return booked_with;
    }

    public void setBooked_with(String booked_with) {
        this.booked_with = booked_with;
    }

    public String getBookingreason() {
        return bookingreason;
    }

    public void setBookingreason(String bookingreason) {
        this.bookingreason = bookingreason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
//    private Not_interested not_interested;

//    public Not_interested getNot_interested() {
//        return not_interested;
//    }
//
//    public void setNot_interested(Not_interested not_interested) {
//        this.not_interested = not_interested;
//    }

    public List<String> getCallResponse() {
        return callResponse;
    }

    public void setCallResponse(List<String> callResponse) {
        this.callResponse = callResponse;
    }

    public Date getScheduledTimestamp() {
        return scheduledTimestamp;
    }

    public void setScheduledTimestamp(Date scheduledTimestamp) {
        this.scheduledTimestamp = scheduledTimestamp;
    }
}
