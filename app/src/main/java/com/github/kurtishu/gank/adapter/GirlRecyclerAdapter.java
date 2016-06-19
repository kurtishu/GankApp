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

package com.github.kurtishu.gank.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.manager.CollectionManager;
import com.github.kurtishu.gank.model.entity.GankEntity;
import com.github.kurtishu.gank.ui.activity.ImageViewerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kurtishu on 4/29/16.
 */
public class GirlRecyclerAdapter extends RecyclerView.Adapter<GirlRecyclerAdapter.ViewHolder> {


    private List<GankEntity> mDatas;
    private Context context;

    public GirlRecyclerAdapter(Context context) {
        this.context = context;
        this.mDatas = new ArrayList<GankEntity>();
    }

    public GirlRecyclerAdapter(List<GankEntity> mDatas) {
        this.mDatas = mDatas;
    }

    public void setDatas(List<GankEntity> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_fragment_girl, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final GankEntity gank = mDatas.get(position);
        if (null != gank) {
            Glide.with(context).load(gank.getUrl()).placeholder(R.mipmap.ic_holder)
                    .crossFade().into(holder.picImage);
            holder.picImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CollectionManager.getInstance().setmEntity(gank);
                    Intent intent = new Intent(context, ImageViewerActivity.class);
                    intent.putExtra("url", gank.getUrl());
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //@Bind(R.id.title)
        //TextView mTitle;

        @Bind(R.id.pretty_image_view)
        ImageView picImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
