package com.roomiegh.roomie.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.roomiegh.roomie.activities.RegistrationActivity;
import com.roomiegh.roomie.models.User;

/**
 * Created by KayO on 18/05/2017.
 */

public class PreferenceData
{
    static final String PREF_LOGGEDIN_USER_EMAIL = "logged_in_email";
    static final String PREF_USER_LOGGEDIN_STATUS = "logged_in_status";
    static final String PREF_PROFILE_PIC_PATH = "profile_pic";
    static final String PREF_PROFILE_F_NAME = "first_name";
    static final String PREF_PROFILE_L_NAME = "last_name";
    static final String PREF_PROFILE_GENDER = "gender";
    static final String PREF_PROFILE_PHONE = "user_phone";
    static final String PREF_PROFILE_PROGRAMME = "programme";
    static final String PREF_PROFILE_NOK = "nok";
    static final String PREF_PROFILE_NOK_PHONE = "nok_phone";
    static final String PREF_PROFILE_YEAR = "year";


    public static SharedPreferences getSharedPreferences(Context ctx)
    {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setLoggedInUserEmail(Context ctx, String email)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LOGGEDIN_USER_EMAIL, email);
        editor.apply();
    }

    public static String getLoggedInEmailUser(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_LOGGEDIN_USER_EMAIL, "");
    }

    public static void setUserLoggedInStatus(Context ctx, boolean status)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_USER_LOGGEDIN_STATUS, status);
        editor.apply();
    }

    public static boolean getUserLoggedInStatus(Context ctx)
    {
        return getSharedPreferences(ctx).getBoolean(PREF_USER_LOGGEDIN_STATUS, false);
    }

    public static void clearProfileData(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_PROFILE_PIC_PATH);
        editor.remove(PREF_PROFILE_F_NAME);
        editor.remove(PREF_PROFILE_L_NAME);
        editor.remove(PREF_PROFILE_PHONE);
        editor.remove(PREF_PROFILE_GENDER);
        editor.remove(PREF_PROFILE_PROGRAMME);
        editor.remove(PREF_PROFILE_NOK);
        editor.remove(PREF_PROFILE_NOK_PHONE);
        editor.remove(PREF_PROFILE_YEAR);
        editor.apply();
    }

    public static void clearLoggedInUserData(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_LOGGEDIN_USER_EMAIL);
        editor.remove(PREF_USER_LOGGEDIN_STATUS);
        editor.apply();
    }

    public static void setProfilePicPath(Context ctx, String path)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_PROFILE_PIC_PATH, path);
        editor.apply();
    }

    public static void clearProfilePic(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_PROFILE_PIC_PATH);
        editor.apply();
    }

    public static void setProfileData(Context ctx, User user)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        //pic path has already been set
        //editor.putString(PREF_PROFILE_PIC_PATH, user.getPicPath());
        editor.putString(PREF_PROFILE_F_NAME, user.getfName());
        editor.putString(PREF_PROFILE_L_NAME, user.getlName());
        editor.putString(PREF_PROFILE_GENDER, user.getGender());
        editor.putString(PREF_PROFILE_PHONE, user.getPhone());
        editor.putString(PREF_PROFILE_PROGRAMME, user.getProgramme());
        editor.putString(PREF_PROFILE_NOK, user.getNok());
        editor.putString(PREF_PROFILE_NOK_PHONE, user.getNokPhone());
        editor.putInt(PREF_PROFILE_YEAR,user.getYear());
        editor.apply();
    }

    public static User getLoggedInUser(Context ctx){
        User currentUser = new User();
        currentUser.setNok(getSharedPreferences(ctx).getString(PREF_PROFILE_NOK, ""));
        currentUser.setNokPhone(getSharedPreferences(ctx).getString(PREF_PROFILE_NOK_PHONE, ""));
        currentUser.setYear(getSharedPreferences(ctx).getInt(PREF_PROFILE_YEAR, 0));
        currentUser.setPicPath(getSharedPreferences(ctx).getString(PREF_PROFILE_PIC_PATH, ""));
        currentUser.setEmail(getSharedPreferences(ctx).getString(PREF_LOGGEDIN_USER_EMAIL, ""));
        currentUser.setfName(getSharedPreferences(ctx).getString(PREF_PROFILE_F_NAME, ""));
        currentUser.setlName(getSharedPreferences(ctx).getString(PREF_PROFILE_L_NAME, ""));
        currentUser.setGender(getSharedPreferences(ctx).getString(PREF_PROFILE_GENDER, ""));
        currentUser.setProgramme(getSharedPreferences(ctx).getString(PREF_PROFILE_PROGRAMME, ""));
        currentUser.setPhone(getSharedPreferences(ctx).getString(PREF_PROFILE_PHONE, ""));
        return currentUser;
    }
}