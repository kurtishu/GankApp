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

package com.github.kurtishu.gank.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;

import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.adapter.CommonRecyclerAdapter;
import com.github.kurtishu.gank.model.entity.GankEntity;
import com.github.kurtishu.gank.presenter.fragment.CommonTabPresenter;
import com.github.kurtishu.gank.ui.view.fragment.ICommonView;
import com.github.kurtishu.gank.wedgit.LoadMoreRecyclerView;
import com.github.kurtishu.gank.wedgit.itemdivider.RecycleViewDivider;

import java.util.List;

import butterknife.Bind;

/**
 * Created by kurtishu on 4/29/16.
 */
public class CommonTabFragment extends BaseFragment<CommonTabPresenter> implements ICommonView {

    @Bind(R.id.data_recyclerview)
    LoadMoreRecyclerView mDataRecyclerView;

    CommonRecyclerAdapter mAdapter;

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_common;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new CommonTabPresenter(this);
        mPresenter.start(getArguments().getString("dataType"));
    }

    public static CommonTabFragment newInstance(String dataType) {
       CommonTabFragment commonTabFragment = new CommonTabFragment();
        Bundle arg = new Bundle();
        arg.putString("dataType", dataType);
        commonTabFragment.setArguments(arg);
        return commonTabFragment;
    }

    @Override
    public void initViews() {

        mDataRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDataRecyclerView.setAutoLoadMoreEnable(true);
        mAdapter = new CommonRecyclerAdapter(getActivity());
        mDataRecyclerView.setAdapter(mAdapter);
        mDataRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
                                              @Override
                                              public void onLoadMore() {
                                                  mPresenter.loadMore();
                                              }
                                          }
        );
        mDataRecyclerView.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.VERTICAL));
        mDataRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void loadDataComplete(List<GankEntity> mData) {
        mAdapter.setDatas(mData);
        mDataRecyclerView.notifyMoreFinish(true);
    }
}
