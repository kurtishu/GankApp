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

package com.github.kurtishu.gank.adapter;

import android.content.Context;
import android.view.View;

import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.adapter.common.CommonAdapter;
import com.github.kurtishu.gank.adapter.common.ViewHolder;
import com.github.kurtishu.gank.db.model.Collection;
import com.github.kurtishu.gank.db.model.Gank;
import com.github.kurtishu.gank.ui.activity.WebActivity;

/**
 * Created by kurtishu on 6/17/16.
 */
public class CollectionAdapter extends CommonAdapter<Collection> {

    public CollectionAdapter(Context context, int itemResId) {
        super(context, itemResId);
    }

    @Override
    public void convert(ViewHolder holder, final Collection item) {
        if (null != item) {
            holder.setText(R.id.desc_text_view, item.desc);
            holder.setText(R.id.who_text_view, item.who);
            holder.setText(R.id.time_text_view, item.publishedTime);
            if ("福利".equals(item.category)) {
                holder.setImageUrl(R.id.fuli_imageview, item.url);
            }

            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebActivity.gotoWebActivity(mContext, item.url, item.desc);
                }
            });
        }
    }
}
