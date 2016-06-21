package com.github.kurtishu.gank.model;

import android.content.Context;

import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.config.GankConst;
import com.github.kurtishu.gank.util.PreferencesUtil;

import java.beans.PropertyChangeListener;

/**
 * Created by kurtishu on 6/21/16.
 */
public class GankTheme extends PropertyObserver {

    private static GankTheme mInstance;
    private String currentTheme;


    private GankTheme() {
        currentTheme = PreferencesUtil.getStringValue(GankConst.KEY_THEME);
    }

    public static GankTheme getInstance() {
        if (null == mInstance) {
            synchronized (GankTheme.class) {
                if (null == mInstance) {
                    mInstance = new GankTheme();
                }
            }
        }

        return mInstance;
    }

    public void setTheme(Context activity) {
        if ("dark".equals(currentTheme)) {
            activity.setTheme(R.style.AppThemeDark);
        } else {
            activity.setTheme(R.style.AppTheme);
        }

    }

    public void changeTheme(String theme) {
        if (!currentTheme.equals(theme)) {
            pcs.firePropertyChange(GankConst.KEY_THEME, currentTheme, theme);
            PreferencesUtil.setStringValue(GankConst.KEY_THEME, theme);
            currentTheme = theme;
        }
    }

    public static void registerThemeChangedListener(PropertyChangeListener listener) {
        getInstance().addPropertyChangeListener(GankConst.KEY_THEME, listener);
    }

    public static void unRegisterThemeChangedListener(PropertyChangeListener listener) {
        getInstance().removePropertyChangeListener(GankConst.KEY_THEME, listener);
    }
}
