package com.daqsoft.auth;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lxx
 */

public class DateUtils {

    public static Date parse(String str) {
        if(StringUtils.isEmpty(str)) {
            return null;
        } else {
            try {
                return (new SimpleDateFormat("yyyy-MM-dd")).parse(str);
            } catch (ParseException var2) {
                return null;
            }
        }
    }
}
