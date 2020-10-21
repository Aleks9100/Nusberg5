package com.example.nusberg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Vibrator;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.nusberg.UserStaticInfo.LOGIN;
import static com.example.nusberg.UserStaticInfo.PASSWORD;

public class Transform {

    public static final String APP_PREFERENCES="mysettings";

    public static void SaveUser(SharedPreferences sp,String login,String password)
    {
        SharedPreferences.Editor e =sp.edit();
        e.putString(LOGIN,login);
        e.putString(PASSWORD,password);
        e.apply();
    }

    public  static Boolean StringNoNull(String string)
    {
        if(string == null) return false;
        else return string.length() != 0;
    }
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
    public static void Vibrate(Context context)
    {
        long mills=1000L;
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator())
        {
            vibrator.vibrate(mills);
        }
    }
    public  static String md5Custome(String st)
    {
        MessageDigest messageDigest = null;
        byte[] digest = new byte[0];
        try {
            messageDigest = MessageDigest.getInstance("MDS");
            messageDigest.reset();
            messageDigest.update(st.getBytes());
            digest=messageDigest.digest();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        BigInteger bigInt=new BigInteger(1,digest);
        String md5Hex=bigInt.toString(16);

        while (md5Hex.length() <32)
        {
            md5Hex="0"+md5Hex;
        }
        return  md5Hex;
    }
}
