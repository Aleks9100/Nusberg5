package com.example.nusberg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

class UserStaticInfo
{
    public static User ActiveUser;
    public  final static String POSITION_LATITUDE="Latitude";
    public final static String POSITION_LONGITUDE="Longitude";
    public final static String POSITION = "position";
    public final static String USERS_SIGN_IN_INFO = "UsersSignInInfo";
    public final static String USERS_PROFILE_INFO="UserProfileInfo";
    public final static String PASSWORD = "password";
    public final static String PROFILE_ID="profileId";
    public final static String NAME = "name";
    public final static String AGE = "age";
    public final static String STATE = "state";
    public static String profileId;
    public static List<User> users=new ArrayList<>();
public final static String LOGIN="login";
    public UserStaticInfo() {
        if(users.size() ==0)
            AddUserInList();
    }
    private void AddUserInList() {
        users.add(new User("dasd", "fas", 13,0));
        users.add(new User("dasd", "fas", 13,1));
        users.add(new User("dasd", "fas", 13,2));
    }
    public static List<Bitmap> photos=new ArrayList<>();

 public UserStaticInfo (Context context)
        {
if(photos.size() == 0) AddPhotosInList(context);
        }

    private void AddPhotosInList(Context context) {
     photos.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.ashe));
     photos.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.brig));
     photos.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.dinamit));
     photos.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.junk));
     photos.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.orisa));
     photos.add(BitmapFactory.decodeResource(context.getResources(),R.drawable.widow));
    }
}
