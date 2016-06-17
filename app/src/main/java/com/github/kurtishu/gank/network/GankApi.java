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

import com.github.kurtishu.gank.model.entity.DailyEntity;
import com.github.kurtishu.gank.model.entity.GankEntity;
import com.github.kurtishu.gank.model.entity.HttpResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by kurtishu on 4/19/16.
 */
public interface GankApi {

    /**
     *
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页

     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     */

    @GET("day/{year}/{month}/{day}")
    Observable<DailyEntity> getDailyData(@Path("year")int year, @Path("month")int month, @Path("day")int day);

    @GET("data/{type}/{pageSize}/{page}")
    Observable<HttpResult<List<GankEntity>>> getGankData(@Path("type") String type, @Path("pageSize") int PageSize, @Path("page") int page);

}
