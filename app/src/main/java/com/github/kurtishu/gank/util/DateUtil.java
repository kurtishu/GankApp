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

import org.joda.time.DateTime;

/**
 * Created by kurtishu on 6/17/16.
 */
public class DateUtil {

    public static String formatUTC(String utcDate) {
        return formatUTC(utcDate, "yyyy-MM-dd hh:mm:ss");
    }

    public static String formatUTC(String utcDate, String pattern) {
        DateTime dateTime = new DateTime(utcDate);
        return dateTime.toString(pattern);
    }

    public static int isHeaderTime(String utc1, String utc2) {
        if (utc1.equals(utc2)) {
            return 0;
        }
        DateTime dateTime1 = new DateTime(utc1);
        DateTime dateTime2 = new DateTime(utc2);
       return dateTime1.isBefore(dateTime2.getMillis()) ? 1 : -1;
    }
}
