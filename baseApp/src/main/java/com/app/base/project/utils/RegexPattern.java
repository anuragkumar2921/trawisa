package com.app.base.project.utils;

public class RegexPattern {
    public static  final String Email = "[\\w\\d.+]+@[\\w\\d]+(?:\\.[a-z]{2,4}){1,2}";

    public static final  String Password = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W]).{8,16})";

}
