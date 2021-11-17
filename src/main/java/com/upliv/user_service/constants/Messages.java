package com.upliv.user_service.constants;

public interface Messages {

    String smsWelcomeMessage = "Congrats %s your account has been successfully created. Explore now %s";
    String smsOtpMessage = "One Time Password for ISTHARA account verification is %s. This code will be valid for 10 minutes";
    String smsInviteMessage = "Hello %s, %s has added you as a family member in %s of %s community.Explore now www.upliv.io";
    //    String smsPasswordChangedMessage = "Your password is successfully changed. In case if you have not request this click here link";
    String smsPasswordChangedMessage = "Your password is successfully changed. In case if you have not requested this send a mail to support@livet.io";

    String emailOtpMessage = "Hello %s, \n" +
            "\n" +
            "Thank you for creating your account on Livet. Your One Time Password to verify your account is %s.\n" +
            "\n" +
            "Note: This OTP is valid for 10 minutes.\n";

    String emailPasswordChangedMessage = "Your password is successfully changed. In case if you have not requested this send a mail to support@livet.io";

}
