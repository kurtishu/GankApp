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

package com.github.kurtishu.gank.wedgit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.github.kurtishu.gank.util.DensityUtil;

public class PagerIndicateView extends View {

    private int totalItems = 5;

    private int currentItemIndex = 1;

    private Paint normalPaint = null;
    private Paint currentPaint = null;
    private int offSet = 0;

    public PagerIndicateView(Context context) {
        super(context);
        init(context);
    }

    public PagerIndicateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PagerIndicateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < totalItems; i++) {
            if (currentItemIndex == i) {
                canvas.drawPoint(offSet * (i + 1), offSet / 2, currentPaint);
            } else {
                canvas.drawPoint(offSet * (i + 1), offSet / 2, normalPaint);
            }
        }
    }

    private void init(Context context) {
        offSet = DensityUtil.dip2px(context, 20);
        normalPaint = new Paint();
        normalPaint.setAntiAlias(true);
        normalPaint.setStyle(Paint.Style.STROKE);
        normalPaint.setStrokeWidth(DensityUtil.dip2px(context, 12));
        normalPaint.setColor(Color.GRAY);
        normalPaint.setStrokeCap(Paint.Cap.ROUND);

        currentPaint = new Paint();
        currentPaint.setAntiAlias(true);
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeWidth(DensityUtil.dip2px(context, 12));
        currentPaint.setColor(Color.BLUE);
        currentPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    public void setCurrentItem(int currentItemIndex) {
        this.currentItemIndex = currentItemIndex;
        invalidate();
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((totalItems + 1) * offSet, offSet);
    }
}
