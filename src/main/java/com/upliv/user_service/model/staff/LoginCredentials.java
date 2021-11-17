package com.upliv.user_service.model.staff;

import com.upliv.user_service.constants.StaffConstants;
import com.upliv.user_service.model.common.ValidPassword;

import java.util.Date;
import java.util.Random;

public class LoginCredentials {

    private String username;
    @ValidPassword
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void generateCredentials(String username){
        setUsername(username);

        Random rdm = new Random();
        int upperLimit = 99;
        int num = rdm.nextInt(upperLimit);
        String pw = "Ist@"+username+"_"+num;
        setPassword(pw);
    }

    public void generateDefaultCredentials() {

        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";


        String pwValues = Capital_chars + Small_chars +
                numbers + symbols;

        String userValues = Capital_chars + Small_chars;

        Random rndm = new Random();

        char[] pw = new char[StaffConstants.PASSWORD_MIN_LENGTH];

        char[] user = new char[StaffConstants.USER_NAME_LENGTH];

        for (int i = 0; i < StaffConstants.PASSWORD_MIN_LENGTH; i++) {
            pw[i] = pwValues.charAt(rndm.nextInt(pwValues.length()));
        }

        for(int i=0 ; i < StaffConstants.USER_NAME_LENGTH; i++){
            user[i] = userValues.charAt(rndm.nextInt(userValues.length()));
        }

        String password = "Ist@13579";//String.valueOf(pw);
        String username = String.valueOf(user);

        setUsername(username);
        setPassword(password);
    }
}
