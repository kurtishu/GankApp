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

package com.github.kurtishu.gank.wedgit;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.util.DensityUtil;

public class ProgressListView extends RelativeLayout {

	private ProgressBar mProgressBar;
	private PinnedHeaderExpandableListView mExpandableListView;
	private boolean mIsLoadingData;

	public ProgressListView(Context context) {
		super(context);
		initial();
	}

	public ProgressListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initial();
	}

	private void initial() {
		LayoutInflater.from(getContext()).inflate(R.layout.view_progress_listview_layout, this);
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
		mExpandableListView = (PinnedHeaderExpandableListView) findViewById(R.id.list_view);
        mExpandableListView.setOnGroupClickListener(mGroupClickListener,false);
        mExpandableListView.setOnHeaderUpdateListener(onHeaderUpdateListener);
	}

	private void loadSuccess() {
		mProgressBar.setVisibility(View.GONE);
		mExpandableListView.setVisibility(View.VISIBLE);
		for (int i = 0; i < getExpandableListAdapter().getGroupCount(); i++) {
			mExpandableListView.expandGroup(i);
		}
	}

	public void loadingData() {
		mProgressBar.setVisibility(View.VISIBLE);
		mExpandableListView.setVisibility(View.GONE);
	}

	public boolean isLoadingData() {
		return mIsLoadingData;
	}

	/**
	 * Sets the adapter that provides data to this view.
	 * 
	 * @param adapter The adapter that provides data to this view.
	 */
	public void setAdapter(ExpandableListAdapter adapter) {
		loadingData();
		mIsLoadingData = true;
		Log.v("ProgressListView", "loading data");
		adapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				loadSuccess();
				mIsLoadingData = false;
				Log.v("ProgressListView", "loading data success");
			}
		});
		mExpandableListView.setAdapter(adapter);
	}

	/**
	 * Gets the adapter that provides data to this view.
	 * 
	 * @return The adapter that provides data to this view.
	 */
	public ExpandableListAdapter getExpandableListAdapter() {
		return mExpandableListView.getExpandableListAdapter();
	}

	/**
	 * 
	 * @param itemLongClickListener
	 */
	public void setOnItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
		mExpandableListView.setOnItemLongClickListener(itemLongClickListener);
	}

	/**
	 * Register a callback to be invoked when an item in this AdapterView has
	 * been clicked.
	 * 
	 * @param onItemClickListener
	 */
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		mExpandableListView.setOnItemClickListener(onItemClickListener);
	}

	/**
	 * 
	 * @param onChildClickListener
	 */
	public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
		mExpandableListView.setOnChildClickListener(onChildClickListener);
	}

	public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener) {
		mExpandableListView.setOnGroupClickListener(onGroupClickListener);
	}

	public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
		mExpandableListView.setOnScrollListener(onScrollListener);
	}

    public void setOnClickListener(PinnedHeaderExpandableListView.OnClickListener viewClickListener) {
        mExpandableListView.setOnClickListener(viewClickListener);
    }

	/**
	 * The number of items owned by the Adapter associated with this
	 * AdapterView. (This is the number of data items, which may be larger than
	 * the number of visible views.)
	 * 
	 * @return
	 */
	public int getCount() {
		return mExpandableListView.getCount();
	}

	/**
	 * Expand a group in the grouped list view
	 * 
	 * @param groupPos
	 */
	public void expandGroup(int groupPos) {
		mExpandableListView.expandGroup(groupPos);
	}

	public long getExpandableListPosition(int position) {
		return mExpandableListView.getExpandableListPosition(position);
	}

	/**
	 * Sets the indicator to be drawn next to a group.
	 * 
	 * @param groupIndicator
	 */
	public void setGroupIndicator(Drawable groupIndicator) {
		mExpandableListView.setGroupIndicator(groupIndicator);
	}

	/**
	 * The Group click listener for disable the expand group.
	 */
	private OnGroupClickListener mGroupClickListener = new OnGroupClickListener() {
		@Override
		public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
			return true;
		}
	};

    /**
     *  The OnHeaderUpdateListener for stick header selection to top on scroll.
     */
    private PinnedHeaderExpandableListView.OnHeaderUpdateListener onHeaderUpdateListener = new PinnedHeaderExpandableListView.OnHeaderUpdateListener() {
        @Override
        public View getPinnedHeader() {
            //Header selection layout
            View headerView = LayoutInflater.from(getContext()).inflate(R.layout.list_section_header, null);
            headerView.setLayoutParams(new LayoutParams(DensityUtil.dip2px(getContext(), 80), LayoutParams.WRAP_CONTENT));
            headerView.setBackgroundResource(R.drawable.bg_selection_header);
            ((TextView)headerView.findViewById(R.id.section_header_text)).setTextColor(Color.WHITE);
            return headerView;
        }

        @Override
        public void updatePinnedHeader(View headerView, int firstVisibleGroupPos) {

            TextView headerTextView = (TextView) headerView.findViewById(R.id.section_header_text);
            // Get group title
            ExpandableListAdapter adapter = mExpandableListView.getExpandableListAdapter();
			if (null != adapter && null != adapter.getGroup(firstVisibleGroupPos)) {
				headerTextView.setText(adapter.getGroup(firstVisibleGroupPos).toString());
                headerView.setVisibility(VISIBLE);
			} else {
                headerView.setVisibility(INVISIBLE);
            }
        }
    };
}
