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

package com.github.kurtishu.gank.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.db.DbHelper;
import com.github.kurtishu.gank.model.entity.GankEntity;
import com.github.kurtishu.gank.presenter.activity.WebPresenter;
import com.github.kurtishu.gank.ui.view.activity.IWebView;
import com.github.kurtishu.gank.wedgit.ProgressWebView;

import butterknife.Bind;

public class WebActivity extends BaseActivity<WebPresenter> implements IWebView {

    private static final String EXTRA_URL = "URL";
    private static final String EXTRA_TITLE = "TITLE";

    @Bind(R.id.wb_content)
    ProgressWebView mWbContent;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    public static void gotoWebActivity(Context context, String url, String title){
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_URL,url);
        intent.putExtra(EXTRA_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_web;
    }

    @Override
    protected void initPresenter() {
        mPresenter = new WebPresenter(this);

        String url = getIntent().getStringExtra(EXTRA_URL);
        mPresenter.setUpWebView(mWbContent);
        mPresenter.loadUrl(mWbContent,url);
    }

    @Override
    public void showLoadErrorMessage(String message) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.nav_collect:
                GankEntity entity = new GankEntity();
                entity.setUrl(getIntent().getStringExtra(EXTRA_URL));
                DbHelper.saveCollection(entity);
                break;
        }
        return true;
    }

    @Override
    public void initViews() {
        String title = getIntent().getStringExtra(EXTRA_TITLE);

        if(!TextUtils.isEmpty(title)){
            initToolBar(toolbar, title, R.mipmap.ic_back);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return true;
    }
}
