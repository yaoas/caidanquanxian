//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.springboot.common.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public final class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {
    public ObjectUtils() {
    }

    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof CharSequence) {
            return ((CharSequence)obj).length() == 0;
        } else if (obj instanceof Collection) {
            return ((Collection)obj).isEmpty();
        } else if (obj instanceof Map) {
            return ((Map)obj).isEmpty();
        } else if (!(obj instanceof Object[])) {
            return false;
        } else {
            Object[] object = (Object[])((Object[])obj);
            if (object.length == 0) {
                return true;
            } else {
                boolean empty = true;

                for(int i = 0; i < object.length; ++i) {
                    if (!isNullOrEmpty(object[i])) {
                        empty = false;
                        break;
                    }
                }

                return empty;
            }
        }
    }

    public static boolean isBaseDataType(Class<?> clazz) {
        Boolean isBaseType = clazz.equals(String.class) || clazz.equals(Integer.class) || clazz.equals(Byte.class) || clazz.equals(Long.class) || clazz.equals(Double.class) || clazz.equals(Float.class) || clazz.equals(Character.class) || clazz.equals(Short.class) || clazz.equals(BigDecimal.class) || clazz.equals(BigInteger.class) || clazz.equals(Boolean.class) || clazz.equals(Date.class) || clazz.isPrimitive();
        return isBaseType;
    }

    public static boolean isEquals(Object object1, Object object2, String[] fields) {
        String[] arr$ = fields;
        int len$ = fields.length;

        for(int i$ = 0; i$ < len$; ++i$) {
            String fieldName = arr$[i$];
            Object object1FieldVal = Reflections.getFieldValue(object1, fieldName);
            Object object2FieldVal = Reflections.getFieldValue(object2, fieldName);
            if (object1FieldVal == null || !object1FieldVal.equals(object2FieldVal)) {
                return Boolean.FALSE;
            }
        }

        return Boolean.TRUE;
    }

    public static void main(String[] args) {
    }

    public static List removeDuplicate(List<String> list) {
        for(int i = 0; i < list.size() - 1; ++i) {
            for(int j = list.size() - 1; j > i; --j) {
                if (list.get(j) != null && list.get(i) != null && ((String)list.get(j)).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }

        return list;
    }
}
