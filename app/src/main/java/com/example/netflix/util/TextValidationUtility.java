package com.example.netflix.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextValidationUtility {

    public static boolean emailValidation(String email) {
        Pattern pattern = Pattern.compile("[a-z0-9]+@[a-z]+\\.[a-z]{2,3}");
        Matcher matcher = pattern.matcher(email);
        return matcher.find() ? true : false;
    }

    public static boolean passwordSpecialCharacterValidation(String password){
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(password);
        return matcher.find();
    }

    public static boolean passwordLengthValidation(String password){
        return password.length() >=8 && password.length() <= 12;
    }

    public static boolean isBothPasswordsEqual(String password,String rePassword){
        return password.equals(rePassword);
    }

}
