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

package com.github.kurtishu.gank.presenter.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

import com.github.kurtishu.gank.model.bean.DailyBean;
import com.github.kurtishu.gank.model.converter.DailyDataConverter;
import com.github.kurtishu.gank.model.entity.DailyEntity;
import com.github.kurtishu.gank.network.GankService;
import com.github.kurtishu.gank.presenter.BasePresenter;
import com.github.kurtishu.gank.ui.view.activity.IHomeView;

import java.util.Calendar;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by kurtishu on 4/19/16.
 */
public class HomePresenter extends BasePresenter<IHomeView> {

    private DatePickerDialog mDatePicker;

    public HomePresenter(IHomeView view) {
        super(view);
    }

    public void showDatePicker(Context context) {
        Calendar calendar = Calendar.getInstance();
        mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                loadDailyData(year, monthOfYear, dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        mDatePicker.show();
    }


    private void loadDailyData(int year, int month, int day) {
        GankService.getGankDailyData(year, month + 1, day)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<DailyEntity, List<DailyBean>>() {
                    @Override
                    public List<DailyBean> call(DailyEntity dailyEntity) {
                        return DailyDataConverter.convert(dailyEntity);
                    }
                })
                .subscribe(new Subscriber<List<DailyBean>>() {
                    @Override
                    public void onCompleted() {
                        Log.d("Kurtis", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Kurtis", "error:" + e.getMessage());
                    }

                    @Override
                    public void onNext(List<DailyBean> dailyBeen) {
                        Log.d("Kurtis", "result:" + dailyBeen.toString());
                        mView.showDailyFragment(dailyBeen);
                    }
                });
    }

}

