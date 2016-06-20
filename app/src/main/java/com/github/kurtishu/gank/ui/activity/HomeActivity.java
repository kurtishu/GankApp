/*
 *
 *  *
 *  *  * Copyright 2016. Kurtis <kurtis_hu@hotmail.com>
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *
 *
 */

package com.github.kurtishu.gank.ui.activity;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.model.bean.DailyBean;
import com.github.kurtishu.gank.presenter.activity.HomePresenter;
import com.github.kurtishu.gank.ui.dialog.InfoDialog;
import com.github.kurtishu.gank.ui.fragment.CategoryFragment;
import com.github.kurtishu.gank.ui.fragment.CollectionFragment;
import com.github.kurtishu.gank.ui.fragment.DailyDataFragment;
import com.github.kurtishu.gank.ui.fragment.GirlTabFragment;
import com.github.kurtishu.gank.ui.view.activity.IHomeView;
import com.github.kurtishu.gank.util.TextUtil;

import java.util.List;

import butterknife.Bind;

public class HomeActivity extends BaseActivity<HomePresenter> implements IHomeView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;

    @Bind(R.id.navigationView)
    NavigationView mNavigationView;

    GirlTabFragment mGirlFragment;
    CategoryFragment mCategoryFragment;
    DailyDataFragment mDailyDataFragment;
    CollectionFragment mCollectionFragment;

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initPresenter() {
      mPresenter = new HomePresenter(this);
    }

    @Override
    public void initViews() {
        initToolBar(toolbar, getString(R.string.app_name), R.mipmap.icon_menu);

        intDefaultFrameLayout();

        initNavigationView();
    }

    private void intDefaultFrameLayout() {
        setSelectedMenu(null);
    }

    private void initNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                item.setChecked(true);
                setTitle(item.getTitle());
                mDrawerLayout.closeDrawers();
                setSelectedMenu(item);
                return true;
            }
        });
    }

    private void setSelectedMenu(MenuItem item) {
        Fragment selectedFragment = null;
        if (null != item) {
            switch (item.getItemId()) {
                case R.id.nav_fuli:
                    if (null == mGirlFragment) {
                        mGirlFragment = GirlTabFragment.newInstance();
                    }
                    selectedFragment = mGirlFragment;
                    toolbar.setTitle(R.string.title_fuli);
                    break;
                case R.id.nav_time:
                    mPresenter.showDatePicker(this);
                    toolbar.setTitle(R.string.title_daily);
                    break;
                case R.id.nav_category:
                    if (null == mCategoryFragment) {
                        mCategoryFragment = CategoryFragment.newInstance();
                    }
                    selectedFragment = mCategoryFragment;
                    toolbar.setTitle(R.string.title_category);
                    break;
                case R.id.nav_collect:
                    if (null == mCollectionFragment) {
                        mCollectionFragment = CollectionFragment.newInstance();
                    }
                    selectedFragment = mCollectionFragment;
                    toolbar.setTitle(R.string.title_mine);
                    break;
                case R.id.setting:
                     startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                    break;
                case R.id.about:
                    InfoDialog info = InfoDialog.newInstance(getString(R.string.nav_about), TextUtil.getInfo());
                    info.show(getSupportFragmentManager(), "about");
                    break;
                default:
                    break;
            }
        } else {
            if (null == mCategoryFragment) {
                mCategoryFragment = CategoryFragment.newInstance();
            }
            selectedFragment = mCategoryFragment;
        }

        if (null != selectedFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit();
        }
    }

    @Override
    public void showDailyFragment(List<DailyBean> entity) {
        if (null == mDailyDataFragment) {
            mDailyDataFragment = DailyDataFragment.newInstance();
        }
        mDailyDataFragment.updateData(entity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, mDailyDataFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
