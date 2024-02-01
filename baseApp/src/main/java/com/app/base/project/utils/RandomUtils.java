package com.app.base.project.utils;

import java.util.Random;

public class RandomUtils {


    public static int generateOTP() {
        Random random = new Random();
        return random.nextInt(90000) + 10000;
    }
}
