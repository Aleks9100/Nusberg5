package com.example.nusberg;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Vibrator;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.nusberg.UserStaticInfo.LOGIN;
import static com.example.nusberg.UserStaticInfo.PASSWORD;

public class Transform {

    public static final String APP_PREFERENCES="mysettings";

    public static Bitmap getRoundedMapBitMap(Bitmap bitmap)
    {
        int iconSize=100;
        Bitmap output=Bitmap.createScaledBitmap(bitmap,iconSize,iconSize,false);
        return getRoundedCornerCubeBitmap(output,iconSize);
    }
    public static void SaveUser(SharedPreferences sp,String login,String password)
    {
        SharedPreferences.Editor e =sp.edit();
        e.putString(LOGIN,login);
        e.putString(PASSWORD,password);
        e.apply();
    }
    public static Bitmap getRoundedCornerCubeBitmap(Bitmap bitmap, float radiusInPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas();
        canvas.setBitmap(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, radiusInPx, radiusInPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
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
