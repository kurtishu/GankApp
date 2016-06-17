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

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.kurtishu.gank.R;
import com.github.kurtishu.gank.presenter.activity.SplashPresenter;
import com.github.kurtishu.gank.ui.view.activity.ISplashView;

import butterknife.Bind;

public class SplashActivity extends BaseActivity<SplashPresenter> implements ISplashView {

    @Bind(R.id.iv_app_icon)
    ImageView mAppIcon;

    @Bind(R.id.tv_app_name)
    TextView mAppName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initPresenter() {
      mPresenter = new SplashPresenter(this);
    }

    @Override
    public void initViews() {
        playAnimation();
    }

    @Override
    public void showHomeScreen() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void showWelcomeSceen() {
        startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
        finish();
    }

    private void playAnimation() {

        PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f, 1f);
        PropertyValuesHolder scaleXHolder = PropertyValuesHolder.ofFloat("scaleX", 1f,
                0, 1f);
        PropertyValuesHolder scaleYHolder = PropertyValuesHolder.ofFloat("scaleY", 1f,
                0, 1f);
        Animator appIconAnimator = ObjectAnimator.ofPropertyValuesHolder(mAppIcon, alphaHolder, scaleXHolder, scaleYHolder);
        Animator appNameAnimator = ObjectAnimator.ofFloat(mAppName, "alpha" , 1f, 0f, 1f);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(appIconAnimator, appNameAnimator);
        set.setDuration(2000);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (null != mPresenter) {
                    mPresenter.isAppFirstInstall();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.setInterpolator(new BounceInterpolator());
        set.start();
    }
}
