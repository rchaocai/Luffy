package com.mmc.lamandys.liba_datapick.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author :xishuang
 * Date :2018-12-06
 * Des :反射工具类
 */
public class ReflectUtil {

    /**
     * 获取当前对象对应字段的属性（对象）
     *
     * @param obj   当前对象
     * @param field 需要获取的属性名
     * @return Object 当前对象指定属性值
     */
    public static Object getFieldValue(Object obj, String field) {
        Class<?> claz = obj.getClass();
        Field f;
        Object fieldValue = null;
        try {
            f = claz.getDeclaredField(field);
            f.setAccessible(true);
            fieldValue = f.get(obj);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fieldValue;
    }


    /**
     * 获取当前对象对应方法的值（对象）
     *
     * @param obj 当前对象
     * @return Object 当前对象方法名
     */
    public static Object getMethodValue(Object obj, String methodName) {

        Class<?> claz = obj.getClass();
        Method method;
        Object methodValue = null;
        try {
            method = claz.getMethod(methodName);
            if (method != null) {
                methodValue = method.invoke(obj);
            }

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return methodValue;
    }

}
