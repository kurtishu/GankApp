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

package com.github.kurtishu.gank.network;

import com.github.kurtishu.gank.config.GankConfig;
import com.github.kurtishu.gank.model.entity.DailyEntity;
import com.github.kurtishu.gank.model.entity.GankEntity;
import com.github.kurtishu.gank.model.entity.HttpResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by kurtishu on 4/19/16.
 */
public class GankService {

    private static GankService mGankService;
    private GankApi gankApi;

    private GankService() {

        OkHttpClient okHttpClient = new OkHttpClient();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GankConfig.API_HOST)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        gankApi = retrofit.create(GankApi.class);
    }

    public static GankApi getGankApi() {
        if (null == mGankService) {
            synchronized (GankService.class) {
                if (null == mGankService) {
                    mGankService = new GankService();
                }
            }
        }
        return mGankService.gankApi;
    }

    public static Observable<HttpResult<List<GankEntity>>> getMeiziData(int page) {
        return getGankApi().getGankData("福利", 20, page);
    }

    public static Observable<HttpResult<List<GankEntity>>> getGankData(String type, int page) {
        return getGankApi().getGankData(type, 20, page);
    }

    public static Observable<DailyEntity> getGankDailyData(int year, int month, int day) {
        return getGankApi().getDailyData(year, month, day);
    }
}
