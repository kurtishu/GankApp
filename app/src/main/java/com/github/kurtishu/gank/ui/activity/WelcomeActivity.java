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

package com.github.kurtishu.gank.ui.activity;

import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.presenter.activity.WelcomePresenter;
import com.github.kurtishu.gank.ui.view.activity.IWelcomeView;

public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements IWelcomeView {

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initPresenter() {
      mPresenter = new WelcomePresenter(this);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void navigationToMainScreen() {

    }
}
