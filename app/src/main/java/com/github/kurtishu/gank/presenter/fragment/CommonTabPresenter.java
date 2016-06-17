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

package com.github.kurtishu.gank.presenter.fragment;

import android.util.Log;

import com.github.kurtishu.gank.model.entity.GankEntity;
import com.github.kurtishu.gank.model.entity.HttpResult;
import com.github.kurtishu.gank.network.GankService;
import com.github.kurtishu.gank.presenter.BasePresenter;
import com.github.kurtishu.gank.ui.view.fragment.ICommonView;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kurtishu on 4/29/16.
 */
public class CommonTabPresenter extends BasePresenter<ICommonView> {

    private int currentPage;
    private List<GankEntity> mData;
    private String mDataType;

    public CommonTabPresenter(ICommonView view) {
        super(view);
        mData = new ArrayList<GankEntity>();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void start(String dataType) {
        mDataType = dataType;
        mData.clear();
        loadData(1);
    }

    public void loadMore() {
        currentPage++;
        loadData(currentPage);
    }

    private void loadData(int currentPage) {
        GankService.getGankData(mDataType, currentPage)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult<List<GankEntity>>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Kurtis", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Kurtis", "error:" + e.getMessage());
                    }

                    @Override
                    public void onNext(HttpResult<List<GankEntity>> listHttpResult) {
                        Log.d("Kurtis", "result:" + listHttpResult.toString());
                        mData.addAll(listHttpResult.getResults());
                        mView.loadDataComplete(mData);
                    }
                });
    }
}
