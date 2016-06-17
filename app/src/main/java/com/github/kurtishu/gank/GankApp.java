package com.github.kurtishu.gank;

import android.content.Context;
import android.os.StrictMode;

/**
 * Created by kurtishu on 4/19/16.
 */
public class GankApp extends com.activeandroid.app.Application{

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDialog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build());

    }

    public static Context getContext() {
        return mContext;
    }
}
