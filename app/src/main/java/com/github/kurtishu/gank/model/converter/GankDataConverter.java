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

import com.github.kurtishu.gank.db.model.Collection;
import com.github.kurtishu.gank.db.model.Gank;
import com.github.kurtishu.gank.model.entity.GankEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kurtishu on 6/16/16.
 */
public class GankDataConverter {

    public static Gank entity2DbModel(GankEntity entity) {
        Gank gank = new Gank();
        gank.category = entity.getType();
        gank.desc = entity.getDesc();
        gank.publishedTime = entity.getPublishedTime();
        gank.url = entity.getUrl();
        gank.who = entity.getWho();
        gank.id = entity.get_id();

        return gank;
    }

    public static GankEntity dbModel2Entity(Gank gank) {
        GankEntity entity = new GankEntity();
        entity.set_id(gank.id);
        entity.setDesc(gank.desc);
        entity.setWho(gank.who);
        entity.setUrl(gank.url);
        entity.setPublishedAt(gank.publishedTime);
        entity.setType(gank.category);
        return entity;
    }

    public static List<Gank> entity2DbModel(List<GankEntity> entityList) {
        List<Gank> gankList = new ArrayList<Gank>();
        for (GankEntity entity : entityList) {
            gankList.add(entity2DbModel(entity));
        }
        return gankList;
    }

    public static List<GankEntity> dbModel2Entity(List<Gank> gankList) {
        List<GankEntity> entityList = new ArrayList<>();
        for (Gank gank : gankList) {
            entityList.add(dbModel2Entity(gank));
        }
        return entityList;
    }

    public static Collection entity2CollectionModel(GankEntity entity) {
        Collection collection = new Collection();
        collection.category = entity.getType();
        collection.desc = entity.getDesc();
        collection.publishedTime = entity.getPublishedTime();
        collection.url = entity.getUrl();
        collection.who = entity.getWho();
        collection.id = entity.get_id();
        collection.collectionTime = System.currentTimeMillis();
        return collection;
    }

}
