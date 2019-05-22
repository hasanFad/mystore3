package com.store.hasanfadool.mystore.sharedPrfrncs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.store.hasanfadool.mystore.models.User;

public class ShPUsers {
    private static final String TAG = "ShPUsers";

    private static final String FILE_NAME = "ShPUsers";
    private static final String USERMAIL_KEY = "userMail";
    private static final String PASSWORD_KEY = "userPassword";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ShPUsers(Context context){
        preferences = context.getSharedPreferences(FILE_NAME , Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void saveUser(String userMail , String userPassword){
        editor.putString(USERMAIL_KEY, userMail);
        editor.putString(PASSWORD_KEY, userPassword);
        editor.commit();
    }

    public User getUser(){
        String userMail = preferences.getString(USERMAIL_KEY, null);
        String userPass = preferences.getString(PASSWORD_KEY, null);
        if (userMail == null || userPass == null){
            Log.d(TAG, "getUser: the userName or the password didn't exist");
            return null;
        }

        return new User(userMail, userPass);
    }


}
