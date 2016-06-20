package com.github.kurtishu.gank.util;

import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;

/**
 * Created by kurtishu on 6/20/16.
 */
public class TextUtil {

    public static CharSequence getInfo() {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        int start = 0;
        builder.append("Gank.IO Android 客户端\n");
        builder.setSpan(new StyleSpan(Typeface.BOLD), start, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append("项目地址：");
        start = builder.length();
        builder.append("github");
        builder.setSpan(new URLSpan ("https://github.com/kurtishu/GankApp"), start, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.append("\n");
        builder.append("感谢 Gank.io 提供的数据支持");
        builder.append("\n");
        start = builder.length();
        builder.append("powered by kurtishu");
        builder.setSpan(new StyleSpan(Typeface.ITALIC), start, builder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }
}
