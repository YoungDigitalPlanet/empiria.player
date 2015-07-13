package eu.ydp.empiria.player.client.test.utils;

import java.lang.reflect.Field;

public class ReflectionsUtils {

    public void setValueInObjectOnField(Class<?> class1, String fieldName, Object object, Object value) throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field field;
        try {
            field = class1.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = getFieldFromSupperClass(class1.getSuperclass(), fieldName);
        }

        field.setAccessible(true);
        field.set(object, value);
    }

    public void setValueInObjectOnField(String fieldName, Object object, Object value) throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        setValueInObjectOnField(object.getClass(), fieldName, object, value);
    }

    public Object getValueFromFiledInObject(String fieldName, Object object) throws SecurityException, NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        return getValueFromFiledInObject(object.getClass(), fieldName, object);
    }

    public Object getValueFromFiledInObject(Class<?> class1, String fieldName, Object object) throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field field;
        try {
            field = class1.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = getFieldFromSupperClass(class1.getSuperclass(), fieldName);
        }
        field.setAccessible(true);
        Object value = field.get(object);
        return value;
    }

    private Field getFieldFromSupperClass(Class<?> superclass, String fieldName) throws NoSuchFieldException {
        if (superclass == null) {
            throw new NoSuchFieldException(fieldName);
        }

        Field field;
        try {
            field = superclass.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            field = getFieldFromSupperClass(superclass.getSuperclass(), fieldName);
        }
        return field;
    }

}
