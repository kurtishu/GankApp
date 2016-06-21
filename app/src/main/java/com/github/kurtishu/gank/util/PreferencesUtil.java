package com.github.kurtishu.gank.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.github.kurtishu.gank.GankApp;

/**
 * Created by kurtishu on 6/21/16.
 */
public class PreferencesUtil {

    private PreferencesUtil () {
    }

    private static SharedPreferences prefs = null;

    static {
       prefs = PreferenceManager.getDefaultSharedPreferences(GankApp.getContext());
    }

    public static void setIntValue(String key, int value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getIntValue(String key) {
        return prefs.getInt(key, 0);
    }

    public static void setLongValue(String key, long value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void setBooleanValue(String key, boolean value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setStringValue(String key, String value) {
        if (value == null) {
            value = "";
        }
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static long getLongValue(String key) {
        return prefs.getLong(key, 0);
    }

    public static boolean getBooleanValue(String key) {
        return prefs.getBoolean(key, true);
    }

    public static String getStringValue(String key) {
        return prefs.getString(key, "");
    }
}
