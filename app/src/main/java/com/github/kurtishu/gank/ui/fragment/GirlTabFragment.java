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

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.adapter.GirlRecyclerAdapter;
import com.github.kurtishu.gank.model.entity.GankEntity;
import com.github.kurtishu.gank.presenter.fragment.GirlTabPresenter;
import com.github.kurtishu.gank.ui.view.fragment.IGirlView;
import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by kurtishu on 4/29/16.
 */
public class GirlTabFragment extends BaseFragment<GirlTabPresenter> implements IGirlView {


    @Bind(R.id.spring_view)
    SpringView mSpringView;

    @Bind(R.id.girl_recyclerview)
    RecyclerView mRecyclerView;

    private GirlRecyclerAdapter mAdapter;


    public static GirlTabFragment newInstance() {
        return new GirlTabFragment();
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_girl;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new GirlTabPresenter(this);
        mPresenter.start();
    }

    @Override
    public void initViews() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mAdapter = new GirlRecyclerAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);

        mSpringView.setHeader(new RotationHeader(getActivity()));
        mSpringView.setFooter(new RotationFooter(getActivity()));
        mSpringView.setListener(new SpringView.OnFreshListener(){
            @Override
            public void onRefresh() {
                mPresenter.start();
            }

            @Override
            public void onLoadmore() {
                mPresenter.loadMore();
            }
        });

    }

    @Override
    public void loadDataComplete(List<GankEntity> mData) {
        mAdapter.setDatas(mData);
        mAdapter.notifyDataSetChanged();
        mSpringView.onFinishFreshAndLoad();
    }
}
