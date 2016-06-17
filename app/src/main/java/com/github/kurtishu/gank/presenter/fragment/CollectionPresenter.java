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

package com.github.kurtishu.gank.presenter.fragment;

import android.util.Log;

import com.github.kurtishu.gank.db.DbHelper;
import com.github.kurtishu.gank.db.model.Collection;
import com.github.kurtishu.gank.presenter.BasePresenter;
import com.github.kurtishu.gank.ui.view.fragment.ICollectionView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * Created by kurtishu on 6/17/16.
 */
public class CollectionPresenter extends BasePresenter<ICollectionView> {

    private List<Collection> mDatas;
    private final ICollectionView mView;
    private int currentPage;

    public CollectionPresenter(ICollectionView view) {
        super(view);
        mDatas = new ArrayList<Collection>();
        mView = view;
    }

    public void start() {
        mDatas.clear();
        currentPage = 0;
        queryCollections(currentPage);
    }

    public void loadMore() {
        currentPage++;
        queryCollections(currentPage);
    }


    private void queryCollections(int currentPage) {
        DbHelper.getCollections(currentPage)
                .subscribe(new Subscriber<List<Collection>>() {

                    @Override
                    public void onCompleted() {
                        Log.d("Kurtis", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Kurtis", "error:" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<Collection> collections) {
                        Log.d("Kurtis", "onNext");
                        mDatas.addAll(collections);
                        mView.loadDataComplete(mDatas);
                    }
                });
    }
}
