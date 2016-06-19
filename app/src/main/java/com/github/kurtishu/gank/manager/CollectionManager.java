
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
package com.github.kurtishu.gank.manager;

import com.github.kurtishu.gank.db.DbHelper;
import com.github.kurtishu.gank.db.model.Collection;
import com.github.kurtishu.gank.model.entity.GankEntity;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * 作者：Kurtis on 2016/6/18 14:19
 * Eevery one should have a dream, what if one day it comes true!
 */
public class CollectionManager {

    private static CollectionManager instance;
    private GankEntity mEntity;


    public static CollectionManager getInstance() {
        if (null == instance) {
            synchronized (CollectionManager.class) {
                if (null == instance) {
                    instance = new CollectionManager();
                }
            }
        }

        return instance;
    }

    public GankEntity getmEntity() {
        return mEntity;
    }

    public void setmEntity(GankEntity mEntity) {
        this.mEntity = mEntity;
    }

    public void saveCollection(Subscriber<Boolean> subscriber ) {
        if (null == mEntity) {
            subscriber.onError(new Exception("GankEntity is null"));
        }

        DbHelper.saveCollection(mEntity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
