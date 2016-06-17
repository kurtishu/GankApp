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

package com.github.kurtishu.gank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

import com.github.kurtishu.gank.ui.fragment.CommonTabFragment;
import com.github.kurtishu.gank.ui.fragment.GirlTabFragment;

/**
 * Created by kurtishu on 4/29/16.
 */
public class CategoryPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 8;
    private String tabTitles[] = new String[]{"福利","Android","iOS", "休息视频","拓展资源","前端", "瞎推荐","App"};
    private SparseArray<Fragment> mFragments = new SparseArray<Fragment>();

    public CategoryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment tabFragment = mFragments.get(position);

        if (null != tabFragment) {
            return tabFragment;
        }
        switch (position) {
            case 0:
                tabFragment = new GirlTabFragment();
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                tabFragment = CommonTabFragment.newInstance(tabTitles[position]);
                break;
        }

        mFragments.put(position, tabFragment);
        return tabFragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
