package com.dz.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName ConcurrentDateUtil
 * @Description TODO
 * @Author zhangdong
 * @Date 2020/11/14 15:39
 * @Version 1.0
 */
public class ConcurrentDateUtil
{

    private static ThreadLocal<DateFormat> dateFormatThreadLocal = new ThreadLocal<>();

    private static  final  String DEFAULT_FOMAT="yyyy-MM-dd HH:mm:ss";

    private static DateFormat getDateFormat(String dateFomat)
    {
        DateFormat dateFormat = dateFormatThreadLocal.get();
        if(null ==dateFormat)
        {
            dateFormat = new SimpleDateFormat(StringUtils.isBlank(dateFomat)?DEFAULT_FOMAT:dateFomat);
            dateFormatThreadLocal.set(dateFormat);
        }

        return  dateFormat;
    }

    public static String dateFormat(long timestamp,String format)
    {

      return   getDateFormat(format).format(new Date(timestamp));
    }

}
