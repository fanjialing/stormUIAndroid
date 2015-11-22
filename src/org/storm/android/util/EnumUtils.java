package org.storm.android.util;

import org.apache.commons.lang.ObjectUtils;  
import org.apache.log4j.Logger;  
  
/** 
 * 枚举的工具类 
 * @author longgangbai 
 * 
 */  
public abstract class EnumUtils {  
    private static final Logger logger = Logger.getLogger(EnumUtils.class);  
  
        /** 
     * 从指定的枚举类中根据property搜寻匹配指定值的枚举实例 
     * @param <T> 
     * @param enumClass 
     * @param property 
     * @param propValue 
     * @return 
     */  
    public static <T extends Enum<T>> T fromEnumProperty(Class<T> enumClass, String property, Object propValue) {  
        T[] enumConstants = enumClass.getEnumConstants();  
        for (T t : enumConstants) {  
            Object constantPropValue;  
            try {  
                constantPropValue = ClassUtils.getDeclaredFieldValue(t, property);  
                if (ObjectUtils.equals(constantPropValue, propValue)) {  
                    return t;  
                }  
            } catch (Exception e) {  
                logger.error(e.getMessage());  
                throw new RuntimeException(e);  
            }  
        }  
        return null;  
    }  
  
    /** 
     * 从指定的枚举类中根据名称匹配指定值 
     * @param <T> 
     * @param enumClass 
     * @param constantName 
     * @return 
     */  
    public static <T extends Enum<T>> T fromEnumConstantName(Class<T> enumClass, String constantName) {  
        T[] enumConstants = enumClass.getEnumConstants();  
        for (T t : enumConstants) {  
            if (((Enum<?>) t).name().equals(constantName)) {  
                return t;  
            }  
        }  
        return null;  
    }  
      
}
