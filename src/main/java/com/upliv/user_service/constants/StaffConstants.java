package com.upliv.user_service.constants;

public class StaffConstants {

    public static int USER_NAME_LENGTH =  8;
    public static int PASSWORD_MIN_LENGTH =  8;

    public enum EMPLOYMENT_STATUS{
        ACTIVE, INACTIVE
    }

    public enum CITY_ASSIGNED{
        HYDERABAD, BANGALORE, DELHI, GURGAON, COIMBATORE
    }

    public enum VERIFICATION_STATUS{
        VERIFIED, PENDING
    }

    public enum KYC_DOCUMENT_TYPE {
        AADHAAR, PAN, VOTER_ID, DRIVING_LICENCE, PASSPORT
    }

    public enum EMPLOYMENT_TYPE{
        FULL_TIME, PART_TIME
    }

    public enum PROPERTY_NAME{
        CHARAN_BOYS_HOSTEL, ISTHARA_COLIVING, ISTHARA_MEN, SWEETY_HOSTEL, GAURAV_PG
    }

    public enum OPERATIONS_DESIGNATION{
        PROPERTY_MANAGER, MANAGER, RESIDENT_SKIPPER, ASSISTANT_MANAGER, GENERAL_MANAGER
    }

    public enum CORPORATE_DESIGNATION{
        MANAGER, COST_CONTROLLER, VICE_PRESIDENT, GENERAL_MANAGER,CTO, CFO, DIRECTOR
    }
//
//    public enum MODE_OF_PAYMENT{
//        UPI, CASH, BANK_TRANSFER
//    }

    public enum Department{
        HR("Human Resources", "HR"),
        SALES("Sales Department", "SALES"),
        KITCHEN("Kitchen Staff", "KITCHEN"),
        MAINTENANCE("Maintenance Staff", "MAINTENANCE"),
        OPERATIONS("Operations Staff", "OPERATIONS"),
        CET("Corporate Executive Team","CET"),
        IT("IT Associate","IT"),
        HOUSE_KEEPING("House Keeping Staff","HOUSE_KEEPING"),
        PROJECTS("Projects Staff","PROJECTS"),
        TELE_MARKETING("Tele Marketing Team","TELE_MARKETING"),
        EVENTS("Events Planning Staff","EVENTS"),
        ACCOUNTS("Accounts Staff","ACCOUNTS"),
        SECURITY("Security Staff","SECURITY"),
        CORPORATE("Corporate Staff","CORPORATE"),
        TRANSPORT("Transportation Staff","TRANSPORT"),
        RATHINAM("Rathinam","RATHINAM"),
        BDM("Business Development Manager","BDM");

        private final String desc;
        private final String name;

        Department(String desc, String name){
            this.desc = desc;
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public String getName() {
            return name;
        }
    }

    public enum ATTENDANCE_FLAG{
        PRESENT, ABSENT, APPROVED_LEAVE, UNAPPROVED_LEAVE, NATIONAL_HOLIDAY
    }

    public enum MONTHS_FOR_ATTENDANCE{
        JANUARY("1", "JANUARY"),
        FEBRUARY("2", "FEBRUARY"),
        MARCH("3", "MARCH"),
        APRIL("4", "APRIL"),
        MAY("5", "MAY"),
        JUNE("6", "JUNE"),
        JULY("7", "JULY"),
        AUGUST("8", "AUGUST"),
        SEPTEMBER("9", "SEPTEMBER"),
        OCTOBER("10", "OCTOBER"),
        NOVEMBER("11", "NOVEMBER"),
        DECEMBER("12", "DECEMBER");

        private final String desc;
        private final String name;

        MONTHS_FOR_ATTENDANCE(String desc, String name){
            this.desc = desc;
            this.name = name;
        }

        public String getDesc() { return desc; }
        public String getName() { return name; }
    }

    public enum timelineActions{
        STAFF_CREATED("Staff Created", "STAFF_CREATED"),
        KYC_UPDATED("Kyc details updated", "KYC_UPDATED"),
        SALARY_UPDATED("Salary details updated", "SALARY_UPDATED"),
        ATTENDANCE_UPDATED("Attendance details updated", ""),
        CALL_DETAILS_UPDATED("Call log updated", "CALL_DETAILS_UPDATED"),
        MESSAGE("Message sent", "MESSAGE"),
        STAFF_REMOVED("Staff removed", ""),
        STATUS_CHANGE("Status has been updated", "STATUS_CHANGE"),
        OTHER_UPDATE("Miscellanoues update", "OTHER_UPDATE");

        private final String desc;
        private final String name;

        timelineActions(String desc, String name){
            this.desc = desc;
            this.name = name;
        }

        public String getDesc() { return desc; }

        public String getName() { return name; }
    }

    public enum ADMIN_TYPE{
        SUPER_ADMIN("Super Admin","SUPER_ADMIN"),
        MODULE_ADMIN("Module Admin", "MODULE_ADMIN"),
        RESOURCE_ADMIN("Resource Admin", "RESOURCE_ADMIN"),
        NO_ACCESS("Normal User, no permissions","NO_ACCESS");

        private final String desc;
        private final String name;

        ADMIN_TYPE(String name,String desc){
            this.name = name;
            this.desc = desc;
        }

        private String getName(){ return name; }
        private String getDesc(){ return desc; }
    }
}