package com.example.nusberg;

import java.util.ArrayList;
import java.util.List;

class UserStaticInfo
{
    public final static String POSITION = "position";
    public static List<User> users=new ArrayList<>();

    public UserStaticInfo() {
        if(users.size() ==0)
            AddUserInList();
    }
    private void AddUserInList() {
        users.add(new User("dasd", "fas", 13,0));
        users.add(new User("dasd", "fas", 13,1));
        users.add(new User("dasd", "fas", 13,2));
    }

}
