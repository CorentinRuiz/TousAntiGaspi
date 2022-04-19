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
}
