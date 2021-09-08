package com.themescoder.androidstore.utils;


import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * ValidateInputs class has different static methods, to validate different types of user Inputs
 **/

public class ValidateInputs {
    
    private static String blockCharacters = "[$&+~;=\\\\?@|/'<>^*()%!-]";


    //*********** Validate Email Address ********//

    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    
    //*********** Validate Name Input ********// 

    public static boolean isValidName(String name) {
        String regExpn = "^([a-zA-Z ]{1,24})+$";
        if (name.equalsIgnoreCase(""))
            return false;
        
        CharSequence inputStr = name;
        Pattern pattern = Pattern.compile(blockCharacters, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return !pattern.matcher(inputStr).find();
    }


    
    //*********** Validate User Login ********//

    public static boolean isValidLogin(String login) {

        String regExpn = "^([a-zA-Z]{4,24})?([a-zA-Z][a-zA-Z0-9_]{4,24})$";
        CharSequence inputStr = login;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }


    
    //*********** Validate Password Input ********//

    public static boolean isValidPassword(String password) {

        String regExpn = "^[a-z0-9_$@.!%*?&]{6,24}$";
        CharSequence inputStr = password;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    

    //*********** Validate Phone Number********//

    public static boolean isValidPhoneNo(String phoneNo) {
        return phoneNo.length()>=10 && Patterns.PHONE.matcher(phoneNo).matches();
    }


    
    //*********** Validate Number Input ********//

    public static boolean isValidNumber(String number) {

        String regExpn = "^[0-9]{1,24}$";
        CharSequence inputStr = number;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

    
    

    //*********** Validate Any Input ********//

    public static boolean isValidInput(String input) {
        String regExpn = "(.*?)?((?:[a-z][a-z]+))";
        if (input.equalsIgnoreCase(""))
            return false;
        
        CharSequence inputStr = input;
        Pattern pattern = Pattern.compile(blockCharacters, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return !pattern.matcher(inputStr).find();
    }

    

    //*********** Validate Search Query ********//

    public static boolean isValidSearchQuery(String query) {

        String regExpn = "^([a-zA-Z]{1,24})?([a-zA-Z][a-zA-Z0-9_]{1,24})$";
        CharSequence inputStr = query;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }

}

