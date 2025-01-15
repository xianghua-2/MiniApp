package com.example.czj.MyToolClass;

public class MyFunction {
    public static boolean checkIsInt(String s){
        for(int i=0;i<s.length();i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9')
                return false;
        }
        return true;
    }
}
