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

package com.github.kurtishu.gank.db;

import android.util.Log;

import com.activeandroid.query.Select;
import com.github.kurtishu.gank.db.dao.GankDao;
import com.github.kurtishu.gank.db.model.Collection;
import com.github.kurtishu.gank.db.model.Gank;
import com.github.kurtishu.gank.model.converter.GankDataConverter;
import com.github.kurtishu.gank.model.entity.GankEntity;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kurtishu on 6/16/16.
 */
public class DbHelper {

    public static void saveMeizhiData(final List<GankEntity> entityList) {
        Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        List<Gank> gankList = GankDataConverter.entity2DbModel(entityList);
                        new GankDao().bulkInsert(gankList);
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        Log.i("DbHelper", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("DbHelper", "saveMeizhiData onError" + e.getMessage());
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        Log.i("DbHelper", "saveMeizhiData" + aBoolean);
                    }
                });
    }

    public static Observable<List<GankEntity>> queryMeizhiData() {
        return Observable.create(new Observable.OnSubscribe<List<GankEntity>>() {
            @Override
            public void call(Subscriber<? super List<GankEntity>> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        List<Gank> gankList = new GankDao().findAll();
                        subscriber.onNext(GankDataConverter.dbModel2Entity(gankList));
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public static Observable<Boolean> saveCollection(final GankEntity entity) {
       return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        Collection collection = GankDataConverter.entity2CollectionModel(entity);
                        collection.save();
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io());
    }

    public static Observable<List<Collection>> getCollections(final int page) {
        return Observable.create(new Observable.OnSubscribe<List<Collection>>() {
            @Override
            public void call(Subscriber<? super List<Collection>> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        List<Collection> collections = new Select().from(Collection.class).offset(page * 10).limit(10).execute();
                        subscriber.onNext(collections);
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
