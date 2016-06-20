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

import com.github.kurtishu.gank.db.DbHelper;
import com.github.kurtishu.gank.model.comparator.GankEntitySortComparator;
import com.github.kurtishu.gank.model.entity.GankEntity;
import com.github.kurtishu.gank.model.entity.HttpResult;
import com.github.kurtishu.gank.network.GankService;
import com.github.kurtishu.gank.presenter.BasePresenter;
import com.github.kurtishu.gank.ui.view.fragment.IGirlView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by kurtishu on 4/29/16.
 */
public class GirlTabPresenter extends BasePresenter<IGirlView> {

    private int currentPage;
    private IGirlView mView;
    private List<GankEntity> mData;

    public GirlTabPresenter(IGirlView view) {
        super(view);
        this.mView = view;
        mData = new ArrayList<GankEntity>();
    }

    public void start() {
        mData.clear();
        currentPage = 1;
        loadMeizhiData(currentPage);
    }

    public void loadMore() {
        currentPage++;
        loadMeizhiData(currentPage);
    }

    private void loadMeizhiData(int currentPage) {
        GankService.getMeiziData(currentPage)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<HttpResult<List<GankEntity>>, Observable<HttpResult<List<GankEntity>>>>() {
                    @Override
                    public Observable<HttpResult<List<GankEntity>>> call(final HttpResult<List<GankEntity>> listHttpResult) {
                        return Observable.create(new Observable.OnSubscribe<HttpResult<List<GankEntity>>>() {
                            @Override
                            public void call(Subscriber<? super HttpResult<List<GankEntity>>> subscriber) {
                                try {
                                    if (!subscriber.isUnsubscribed()) {
                                        HttpResult<List<GankEntity>> result = listHttpResult;
                                        List<GankEntity> entities = result.getResults();
                                        Collections.sort(entities, new GankEntitySortComparator());
                                        result.setResults(entities);
                                        subscriber.onNext(result);
                                        subscriber.onCompleted();
                                    }
                                } catch (Exception e) {
                                    subscriber.onError(e);
                                }
                            }
                        });
                    }
                })
                .subscribe(new Subscriber<HttpResult<List<GankEntity>>>() {

                    @Override
                    public void onCompleted() {
                        Log.d("Kurtis", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Kurtis", "error:" + e.getMessage());
                        loadMeizhiDataFromDb();
                    }

                    @Override
                    public void onNext(HttpResult<List<GankEntity>> listHttpResult) {
                        Log.d("Kurtis", "result:" + listHttpResult.toString());
                        mData.addAll(listHttpResult.getResults());
                        mView.loadDataComplete(mData);
                        if (null != mData) {
                            DbHelper.saveMeizhiData(mData);
                        }
                    }
                });
    }
    private void loadMeizhiDataFromDb() {
        DbHelper.queryMeizhiData()
                .subscribe(new Subscriber<List<GankEntity>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Kurtis", "queryMeizhiData onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Kurtis", "queryMeizhiData error:" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<GankEntity> entityList) {
                        Log.d("Kurtis", "queryMeizhiData:" + entityList.toString());
                        mData.clear();
                        mData.addAll(entityList);
                        mView.loadDataComplete(mData);
                    }
                });
    }
}
