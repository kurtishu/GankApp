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

package com.github.kurtishu.gank.db.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by kurtishu on 6/16/16.
 */

@Table(name="Gank")
public class Gank extends Model {

    @Column(name = "_id" , unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String gankId;

    @Column(name = "desc")
    public String desc;

    @Column(name = "who")
    public String who;

    @Column(name = "category")
    public String category;

    @Column(name = "url")
    public String url;

    @Column(name = "publishedAt")
    public String publishedTime;
}
