package com.example.netflix.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextValidationUtility {

    public static boolean emailValidation(String email) {
        Pattern pattern = Pattern.compile("[a-z0-9]+@[a-z]+\\.[a-z]{2,3}");
        Matcher matcher = pattern.matcher(email);
        return matcher.find() ? true : false;
    }

    public static boolean passwordValidation(String password){
        Pattern pattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = pattern.matcher(password);
        boolean validPassword = matcher.find();
        if (password.length() > 8 && password.length() < 12 && validPassword)
            return true;
        else return false;
    }

    public static boolean isBothPasswordsEqual(String password,String rePassword){
        return password.equals(rePassword);
    }

}
