package com.github.kurtishu.gank.ui.fragment;


import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.model.GankTheme;
import com.github.kurtishu.gank.util.SystemUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends PreferenceFragment {


    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        initViews();
    }

    private void initViews() {
        final ListPreference themePref = (ListPreference)findPreference(getString(R.string.key_theme));
        themePref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                GankTheme.getInstance().changeTheme(String.valueOf(newValue));
                Toast.makeText(getActivity(), "设置成功", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        final Preference versionPref = findPreference(getString(R.string.key_version));
        versionPref.setSummary(SystemUtil.getAppVersionName(getActivity()));
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
}
