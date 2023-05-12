package com.easyride.urbanBusTransit.validators;




public class Validator {
    public static boolean isValidEmailAddress(String emailAddress){
        return emailAddress.contains("@");
    }
    public static boolean isValidPassword(String password){
        return password.matches("[a-zA-Z0-9%*]{8,20}");

    }
    public static boolean isValidPhoneNumber(String phoneNumber){
        return phoneNumber.length()==11;
    }
}
