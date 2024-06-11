package com.example.newsandroidproject.common;

public class NumParser {
    public static String numParse(Long n) {
        String sn = "";
        if(n < 10000){
            sn = String.valueOf(n);
        }
        else if(n >= 10000){
            sn = String.valueOf((float)n/1000) + "N";
        }else if(n >= 1000000){
            sn = String.valueOf((float)n/1000000) + "Tr";
        }
        else if(n >= 1000000000){
            sn = String.valueOf((float)n/1000000000) + "T";
        }
        return sn;
    }
}
