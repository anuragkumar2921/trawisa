package com.app.base.project.utils;

import java.util.regex.Pattern;

public class BaseValidationUtils {
    public static Boolean isValidEmail(String email) {
        return Pattern.compile(RegexPattern.Email).matcher(email).matches();

    }

    public static Boolean isValidPassword(String password) {
        return Pattern.compile(RegexPattern.Password).matcher(password).matches();
    }
}
