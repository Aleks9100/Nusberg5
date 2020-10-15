package com.example.nusberg;

public class Transform {
public static int parseIntOrDefault(String whatParse,int defaultValue)
    {
     int parse;
     try {
         parse=Integer.parseInt(whatParse);
     }
     catch (Exception ex)
     {
         parse=defaultValue;
     }
     return parse;
    }
}
