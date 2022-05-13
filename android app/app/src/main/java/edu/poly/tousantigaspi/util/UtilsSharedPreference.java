package edu.poly.tousantigaspi.util;

import android.content.Context;
import android.content.SharedPreferences;

public class UtilsSharedPreference {

    public static void pushStringToPref(Context context, String key, String value){
        SharedPreferences pref = context.getSharedPreferences("pref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key,value);
        editor.apply();
    }

    public static String getStringFromPref(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences("pref",Context.MODE_PRIVATE);
        return pref.getString(key,"");
    }

    public static void pushIntToPref(Context context, String key, Integer value){
        SharedPreferences pref = context.getSharedPreferences("pref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(key,value);
        editor.apply();
    }

    public static Integer getIntFromPref(Context context, String key, Integer defaultValue){
        if(defaultValue == null){
            defaultValue = 0;
        }
        SharedPreferences pref = context.getSharedPreferences("pref",Context.MODE_PRIVATE);
        return pref.getInt(key,defaultValue);
    }

    public static void pushBooleanToPref(Context context, String key, Boolean value){
        SharedPreferences pref = context.getSharedPreferences("pref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key,value);
        editor.apply();
    }

    public static Boolean getBooleanFromPref(Context context, String key, Boolean defaultValue){
        if(defaultValue == null){
            defaultValue = true;
        }
        SharedPreferences pref = context.getSharedPreferences("pref",Context.MODE_PRIVATE);
        return pref.getBoolean(key,defaultValue);
    }

}

