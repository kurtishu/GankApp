/*
 *
 *  *
 *  *  *
 *  *  *  * Copyright 2016. Kurtis <kurtis_hu@hotmail.com>
 *  *  *  *
 *  *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  *  * you may not use this file except in compliance with the License.
 *  *  *  * You may obtain a copy of the License at
 *  *  *  *
 *  *  *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *  *  *
 *  *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  *  * See the License for the specific language governing permissions and
 *  *  *  * limitations under the License.
 *  *  *
 *  *
 *
 */

package com.github.kurtishu.gank.ui.fragment;

import android.os.Handler;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.adapter.MyExpandableListAdapter;
import com.github.kurtishu.gank.model.bean.DailyBean;
import com.github.kurtishu.gank.model.entity.GankEntity;
import com.github.kurtishu.gank.presenter.fragment.DailyPresenter;
import com.github.kurtishu.gank.ui.activity.WebActivity;
import com.github.kurtishu.gank.ui.view.fragment.IDailyView;
import com.github.kurtishu.gank.wedgit.ProgressListView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by kurtishu on 6/14/16.
 */
public class DailyDataFragment extends BaseFragment<DailyPresenter> implements IDailyView {

    private static final String KEY_DAILY_DATA = "key_daily_data";
    @Bind(R.id.daily_list_view)
    ProgressListView mProgressListView;

    @Bind(R.id.no_result_text)
    TextView mNoResultTextView;

    MyExpandableListAdapter mListAdapter;

    private List<DailyBean> mDailyBeanList;

    public static DailyDataFragment newInstance() {
        return new DailyDataFragment();
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_daily;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new DailyPresenter(this);
    }

    @Override
    public void initViews() {
        mNoResultTextView.setVisibility(View.GONE);
        mProgressListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                GankEntity entity = (GankEntity) mListAdapter.getChild(groupPosition, childPosition);
                if (null != entity) {
                    WebActivity.gotoWebActivity(getActivity(), entity.getUrl(), entity.getDesc());
                }

                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != mDailyBeanList) {
            mListAdapter = new MyExpandableListAdapter(getActivity(), mDailyBeanList);
            mProgressListView.setAdapter(mListAdapter);
            mListAdapter.notifyDataSetChanged();
            mNoResultTextView.setVisibility(View.GONE);
        } else {
            mNoResultTextView.setVisibility(View.VISIBLE);
        }
    }

    public void updateData(final List<DailyBean> dailyBeanList) {
        mDailyBeanList = dailyBeanList;
        if (null == getActivity()) return;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (null != dailyBeanList && dailyBeanList.size() > 0) {
                    mListAdapter.setData(dailyBeanList);
                    mListAdapter.notifyDataSetChanged();
                    mNoResultTextView.setVisibility(View.GONE);
                } else {
                    mNoResultTextView.setVisibility(View.VISIBLE);
                }
            }
        }, 1000);
    }
}
