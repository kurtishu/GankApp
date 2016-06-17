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

package com.github.kurtishu.gank.util;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseArray;

/**
 * Created by kurtishu on 6/17/16.
 */

public class TypefaceManager {

    private final static int CUSTOMSTYLE_CRS = 1;
    private final static int CUSTOMSTYLE_HTU = 2;
    private final static int CUSTOMSTYLE_HTUO = 3;

    private final static String CUSTOMFONT_CRS = "Classic_round_smooth.TTF";
    private final static String CUSTOMFONT_HTU = "HelveticaNeueLTPro-UltLtEx.otf";
    private final static String CUSTOMFONT_HTUO = "HelveticaNeueLTPro-UltLtExO.otf";

    private static final SparseArray<Typeface> typeFaceCache = new SparseArray<Typeface>();

    /**
     * Get custom font from cache, load custom font from local fonts file
     * if the selected font do not exist in cache,
     * then store the loaded font into cache.
     *
     * @param customTag
     *            - Tag for custom font
     * @return
     *         - Custom Typeface.
     */
    public static Typeface getTypeFaceByCustomTag(Context context,int customTag) {
        Typeface tf = null;
        try {
            customTag = (customTag == 0) ? CUSTOMSTYLE_CRS : customTag;
            if (null != typeFaceCache && null != typeFaceCache.get(customTag)) {
                tf = typeFaceCache.get(customTag);
            } else {
                String customFont = getCustomFontByCustomTag(customTag);
                tf = loadCustomTypeface(context, customFont, customTag);
            }
        } catch (final Exception e) {
            Log.e("CustomTypeface", "Could not get typeface: " + e.getMessage());
        }
        return tf;
    }

    /**
     * Get custom font though the custom tag which had set in XML file.
     *
     * @param customStyle
     *            - Custom font tag.
     * @return
     *         - Custom font.
     */
    private static String getCustomFontByCustomTag(int customStyle) {
        String customFont = CUSTOMFONT_CRS;
        if(CUSTOMSTYLE_CRS == customStyle) {
            customFont = CUSTOMFONT_CRS;
        } else if(CUSTOMSTYLE_HTU == customStyle) {
            customFont = CUSTOMFONT_HTU;
        } else if (CUSTOMSTYLE_HTUO == customStyle) {
            customFont = CUSTOMFONT_HTUO;
        }
        return customFont;
    }


    /**
     * Load custom font from file.
     *
     * @param context
     *            - Activity context
     * @param customFont
     *            - Custom font.
     * @param customTag
     *            - Custom tag
     * @return
     *         - Typeface which is load from fonts file
     */
    private static Typeface loadCustomTypeface(Context context,
                                               String customFont, int customTag) {
        Typeface tf = null;
        try {
            tf = Typeface.createFromAsset(context.getAssets(), "fonts/"
                    + customFont);
            typeFaceCache.put(customTag, tf);
        } catch (final Exception e) {
            Log.e("CustomTypeface", "Could not get typeface: " + e.getMessage());
        }
        return tf;
    }
}
