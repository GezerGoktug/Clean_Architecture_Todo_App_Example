package com.todoapp.application.utils;

import java.lang.reflect.Field;

public class ClassUtils {

    public static void copyProperties(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }

        Class<?> srcClass = source.getClass();

        while (srcClass != null) {
            Field[] fields = srcClass.getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);

                    Field targetField = findField(target.getClass(), field.getName());
                    if (targetField == null) {
                        continue; // targetta hiç yoksa geç
                    }

                    targetField.setAccessible(true);
                    targetField.set(target, field.get(source));
                } catch (Exception e) {
                    // ignore
                }
            }
            srcClass = srcClass.getSuperclass(); // source için super class'a geç
        }
    }

    private static Field findField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass(); // target için super class'a geç
            }
        }
        return null;
    }
}
