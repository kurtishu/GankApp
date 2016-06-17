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
import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.model.bean.DailyBean;
import com.github.kurtishu.gank.model.entity.GankEntity;

import java.util.List;

/**
 * Created by kurtishu on 6/14/16.
 */
public class MyExpandableListAdapter implements ExpandableListAdapter {

    private final DataSetObservable mDataSetObservable = new DataSetObservable();

    private List<DailyBean> mDailyEntity;
    private Context mContext;

    public MyExpandableListAdapter(Context context, List<DailyBean> dailyEntity) {
        this.mContext = context;
        this.mDailyEntity = dailyEntity;
    }

    public void registerDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.registerObserver(observer);
    }

    public void unregisterDataSetObserver(DataSetObserver observer) {
        mDataSetObservable.unregisterObserver(observer);
    }

    public void notifyDataSetInvalidated() {
        mDataSetObservable.notifyInvalidated();
    }

    public void notifyDataSetChanged() {
        mDataSetObservable.notifyChanged();
    }

    public void setData(List<DailyBean> dailyEntity) {
        if (null != mDailyEntity) {
            mDailyEntity.clear();
        }
        mDailyEntity = dailyEntity;
    }

    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public int getGroupCount() {
        return mDailyEntity.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (null != mDailyEntity.get(groupPosition)) {
            return mDailyEntity.get(groupPosition).getResults().size();
        }

        return 0;
    }

    @Override
    public String getGroup(int groupPosition) {
        return mDailyEntity.get(groupPosition).getCategory();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        List<GankEntity> entities = mDailyEntity.get(groupPosition).getResults();
        if (null != entities) {
            return entities.get(childPosition);
        }

       return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_section_header, null);
        }

        TextView tv = (TextView) convertView.findViewById(R.id.section_header_text);
        tv.setText(getGroup(groupPosition));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_section_child, null);
        }
        TextView desTextView = (TextView) convertView.findViewById(R.id.desc_text_view);
        TextView showTextView = (TextView) convertView.findViewById(R.id.who_text_view);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.time_text_view);
        ImageView childImageView = (ImageView) convertView.findViewById(R.id.child_image_view);
        GankEntity entity = (GankEntity)getChild(groupPosition, childPosition);
        if (null != entity) {
            desTextView.setText(entity.getDesc());
            showTextView.setText(entity.getWho());
            timeTextView.setText(entity.getPublishedAt());
            if (entity.getType().equals("福利")) {
                childImageView.setVisibility(View.VISIBLE);
                Glide.with(mContext).load(entity.getUrl()).placeholder(R.mipmap.ic_holder)
                        .crossFade().into(childImageView);
            } else {
                childImageView.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }
}
