package com.daqsoft.utils;

import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Date;

/**
 * @author lxx
 */

public class RequestUtils {

    public static Object getLoginRequest(HttpServletRequest request, Class clazz) {
        try {
            Field[] fileds = clazz.getDeclaredFields();
            Object obj = clazz.newInstance();
            Field[] var4 = fileds;
            int var5 = fileds.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Field f = var4[var6];
                if((f.getModifiers() & 16) <= 0 && (f.getModifiers() & 8) <= 0) {
                    f.setAccessible(true);
                    Class<?> fieldType = f.getType();
                    String fieldName = f.getName();
                    String paramVal = request.getParameter(fieldName);
                    if(String.class == fieldType) {
                        f.set(obj, paramVal);
                    } else if(Long.TYPE != fieldType && Long.class != fieldType) {
                        if(Integer.TYPE != fieldType && Integer.class != fieldType) {
                            if(Date.class == fieldType) {
                                f.set(obj, DateUtils.parse(paramVal));
                            }
                        } else {
                            f.set(obj, Integer.valueOf(NumberUtils.toInt(paramVal)));
                        }
                    } else {
                        f.set(obj, Long.valueOf(NumberUtils.toLong(paramVal)));
                    }
                }
            }

            return obj;
        } catch (Exception var11) {
            return null;
        }
    }
}
