package com.upliv.user_service.constants;

public class UserConstants {

    public static String BRAND_NAME = "ISTHARA";

    public enum Gender {
        MALE, FEMALE, OTHER
    }

    public enum StaffRoles {
        SECURITY,HOUSEKEEPING,ADMIN
    }

    public enum VendorServiceType {
        SECURITY_COMPANY,HOUSEKEEPING_COMPANY
    }

    public enum AuthenticationType {
        EMAIL_ONLY,PHONE_ONLY,EMAIL_OR_PHONE
    }

    public enum MARITAL_STATUS {
        SINGLE, MARRIED
    }

    public enum DB_SEQUENCE_TYPE {
        STAFF
    }

    public enum PLATFORM_TYPE {ANDROID,IOS, WEB}

    public enum REQUEST_TYPE{
        CHECK_IN, CHECK_OUT
    };
}
