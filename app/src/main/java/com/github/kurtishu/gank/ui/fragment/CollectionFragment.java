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

import android.widget.ListView;

import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.adapter.CollectionAdapter;
import com.github.kurtishu.gank.db.model.Collection;
import com.github.kurtishu.gank.presenter.fragment.CollectionPresenter;
import com.github.kurtishu.gank.ui.view.fragment.ICollectionView;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by kurtishu on 6/17/16.
 */
public class CollectionFragment extends BaseFragment<CollectionPresenter> implements ICollectionView {

    @Bind(R.id.spring_view)
    SpringView mSpringView;

    @Bind(R.id.collction_list_view)
    ListView mCollectionList;

    CollectionAdapter mAdapter;

    public static CollectionFragment newInstance() {
        return new CollectionFragment();
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_collection;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new CollectionPresenter(this);
        mPresenter.start();
    }

    @Override
    public void initViews() {
        mAdapter = new CollectionAdapter(getActivity(), R.layout.item_fragment_collection);
        mCollectionList.setAdapter(mAdapter);

        mSpringView.setHeader(new DefaultHeader(getActivity()));
        mSpringView.setFooter(new DefaultFooter(getActivity()));
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
    public void loadDataComplete(List<Collection> mData) {
        if (null != mData && mData.size() > 0) {
            mAdapter.addDatas(mData);
            mAdapter.notifyDataSetChanged();
        }
    }
}
