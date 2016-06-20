package com.github.kurtishu.gank.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.presenter.fragment.SettingPresenter;
import com.github.kurtishu.gank.ui.fragment.SettingFragment;
import com.github.kurtishu.gank.ui.view.fragment.ISettingView;

import butterknife.Bind;

public class SettingsActivity extends BaseActivity<SettingPresenter> implements ISettingView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void initPresenter() {
      mPresenter = new SettingPresenter(this);
    }

    @Override
    public void initViews() {
        initToolBar(toolbar, getString(R.string.title_setting), R.mipmap.ic_back);
        getFragmentManager().beginTransaction()
                .replace(R.id.content_frame, SettingFragment.newInstance())
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }
}
