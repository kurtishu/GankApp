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

package com.github.kurtishu.gank.model.converter;

import com.github.kurtishu.gank.model.bean.DailyBean;
import com.github.kurtishu.gank.model.entity.DailyEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by kurtishu on 6/15/16.
 */
public class DailyDataConverter {

    public static List<DailyBean> convert(DailyEntity dailyEntity) {
        List<String> categories = dailyEntity.getCategory();
        List<DailyBean> dailyBeanList = new ArrayList<DailyBean>();

        for (String category : categories) {
            DailyBean dailyBean = new DailyBean();
            dailyBean.setCategory(category);
            if ("福利".equals(category)) {
                dailyBean.setResults(dailyEntity.getResults().福利);
                dailyBean.setOrder(0);
            } else if ("Android".equals(category)) {
                dailyBean.setResults(dailyEntity.getResults().Android);
                dailyBean.setOrder(1);
            } else if ("iOS".equals(category)) {
                dailyBean.setResults(dailyEntity.getResults().iOS);
                dailyBean.setOrder(2);
            } else if ("休息视频".equals(category)) {
                dailyBean.setResults(dailyEntity.getResults().休息视频);
                dailyBean.setOrder(7);
            } else if ("拓展资源".equals(category)) {
                dailyBean.setResults(dailyEntity.getResults().拓展资源);
                dailyBean.setOrder(4);
            } else if ("App".equals(category)) {
                dailyBean.setResults(dailyEntity.getResults().App);
                dailyBean.setOrder(6);
            } else if ("前端".equals(category)) {
                dailyBean.setResults(dailyEntity.getResults().前端);
                dailyBean.setOrder(3);
            } else if ("瞎推荐".equals(category)) {
                dailyBean.setResults(dailyEntity.getResults().瞎推荐);
                dailyBean.setOrder(5);
            }

            dailyBeanList.add(dailyBean);
        }

        // Sort the result
        SortComparator sortComparator = new SortComparator();
        Collections.sort(dailyBeanList, sortComparator);

        return dailyBeanList;
    }
}
