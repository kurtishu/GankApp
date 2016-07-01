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

package com.github.kurtishu.gank.presenter.activity;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.kurtishu.gank.GankApp;
import com.github.kurtishu.gank.config.GankConfig;
import com.github.kurtishu.gank.presenter.BasePresenter;
import com.github.kurtishu.gank.ui.view.activity.IImageDetailView;
import com.github.kurtishu.gank.util.FileUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by kurtishu on 6/14/16.
 */
public class ImageViewerPresenter extends BasePresenter<IImageDetailView> {

    public ImageViewerPresenter(IImageDetailView view) {
        super(view);
    }

    public void downloadPic(final String url) {

        Glide.with(GankApp.getContext()).load(url).downloadOnly(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                saveFileToSDCard(resource);
            }
        });
    }

    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        return format.format(date);
    }

    private void saveFileToSDCard(final File source) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                try {
                    String sdPath = FileUtil.getSDPath();
                    File dirPath = new File(sdPath + GankConfig.FILEDIR);
                    if (!dirPath.exists()) {
                        dirPath.mkdir();
                    }
                    String filePath = dirPath.getAbsolutePath() + File.separator + getCurrentTime() + ".jpg";
                    FileUtil.copyFile(source, new File(filePath));
                    subscriber.onNext(filePath);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(new Exception("filePath is null"));
                }
            }
        }).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(GankApp.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(String s) {
                        Toast.makeText(GankApp.getContext(), "" + s, Toast.LENGTH_LONG).show();
                    }
                });
    }
}
