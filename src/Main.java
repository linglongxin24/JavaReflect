import com.sun.javafx.beans.annotations.NonNull;
import mode.History;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Main {


    public static void main(String[] args) {
        System.out.println("Hello World!");
        System.out.println("getClassName=" + getClassName(History.class));
        System.out.println("getAttributeNames=" + Arrays.toString(getAttributeNames(History.class)));
        System.out.println("getAttributeType=" + Arrays.toString(getAttributeType(History.class)));
        History history = new History();
        history.setId(1);
        history.setContent("这是内容");
        history.setTime(new Date().toString());
        System.out.println("getAllFiledInfo=" + getAllFiledInfo(history));
    }

    /**
     * 获取类的简名，不包含包名
     *
     * @param c 类
     * @return 类名
     */
    public static String getClassName(Class c) {
        if (c == null) {
            return null;
        }
        return c.getSimpleName();
    }

    /**
     * 获取类的属性名
     *
     * @param c 类
     * @return 所有属性的数组
     */
    public static String[] getAttributeNames(Class c) {
        if (c == null) {
            return null;
        }
        Field[] declaredFields = new Field[0];
        try {
            declaredFields = c.getDeclaredFields();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        String[] attributes = new String[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            attributes[i] = declaredFields[i].getName();
        }
        return attributes;
    }

    /**
     * 获取类的属性类型
     *
     * @param c 类
     * @return 所有属性类型的数组
     */
    public static Object[] getAttributeType(Class c) {
        if (c == null) {
            return null;
        }
        Field[] declaredFields = new Field[0];
        try {
            declaredFields = c.getDeclaredFields();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        Object[] attributes = new Object[declaredFields.length];
        for (int i = 0; i < declaredFields.length; i++) {
            attributes[i] = declaredFields[i].getType();
        }
        return attributes;
    }

    /**
     * 获取类的属性名获取属性值
     *
     * @param o 类对象
     * @return 所对应的属性值
     */
    public static Object getValueByAttribute(Object o, String attribute) {
        if (o == null) {
            return null;
        }
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        String getMethodName = "get" + attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        try {
            Method method = o.getClass().getMethod(getMethodName, null);
            Object value = method.invoke(o, null);
            return value;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取类的属性名获取属性值
     *
     * @param o 类对象
     * @return 所对应的属性值
     */
    public static List<Map<String, Object>> getAllFiledInfo(Object o) {
        if (o == null) {
            return null;
        }
        String[] attributes = getAttributeNames(o.getClass());
        Object[] attributeTypes = getAttributeType(o.getClass());
        List<Map<String,Object>> allFiledInfos=new ArrayList<>();
        for (int i = 0; i < attributes.length; i++) {
            Map<String,Object> allFiledInfo=new HashMap<>();
            allFiledInfo.put("name",attributes[i]);
            allFiledInfo.put("type",attributeTypes[i]);
            allFiledInfo.put("value",getValueByAttribute(o,attributes[i]));
            allFiledInfos.add(allFiledInfo);
        }
        return allFiledInfos;
    }
}
