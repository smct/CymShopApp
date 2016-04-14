package com.cym.cymshopapp.utils;

import android.content.Context;

import com.cym.cymshopapp.Contant;
import com.cym.cymshopapp.bean.User;

/**
 * ========================
 * CYM
 */
public class UserLocalData {


    public static User getUser(Context context) {
        String user_json = PreferencesUtils.getString(context, Contant.USER_JSON, null);
        if (user_json == null) {
            return null;
        }
        return JSONUtil.fromJson(user_json, User.class);
    }

    public static void putToken(String token, Context context) {

        PreferencesUtils.putString(context, Contant.TOKEN, token);
    }

    public static void putUser(User user, Context context) {
        String user_json = JSONUtil.toJSON(user);
        PreferencesUtils.putString(context, Contant.USER_JSON, user_json);
    }

    public static String getToken(Context context) {
        String token = PreferencesUtils.getString(context, Contant.TOKEN, null);
        if (token == null) {
            return null;
        }
        return token;
    }

    public static  void clearUser(Context context) {
        PreferencesUtils.putString(context, Contant.USER_JSON, "");
    }

    public static void clearToken(Context context) {
        PreferencesUtils.putString(context, Contant.TOKEN, "");
    }
}
