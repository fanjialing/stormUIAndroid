package org.storm.android.util;

import java.lang.reflect.Array;  
import java.lang.reflect.Constructor;  
import java.lang.reflect.Field;  
import java.lang.reflect.Member;  
import java.lang.reflect.Method;  
import java.lang.reflect.Modifier;  
import java.util.HashSet;  
import java.util.Set;  
import java.util.logging.Logger;  
import org.apache.commons.lang.ArrayUtils;  
  
/** 
 *  
 * 反射的工具类 
 *  
 *  
 */  
public abstract class ClassUtils {  
  
    private static Logger logger = Logger.getLogger(ClassUtils.class.getName());  
  
    public ClassUtils() {  
    }  
  
    /** 
     * 通过类加载机制返回类对象 
     *  
     * @param name 
     * @param classLoader 
     * @return 
     * @throws ClassNotFoundException 
     */  
    @SuppressWarnings("unchecked")  
    public static Class forName(String name, ClassLoader classLoader)  
            throws ClassNotFoundException {  
        Class clazz = resolvePrimitiveClassName(name);  
        if (clazz != null)  
            return clazz;  
        if (name.endsWith("[]")) {  
            String elementClassName = name.substring(0, name.length()  
                    - "[]".length());  
            Class elementClass = forName(elementClassName, classLoader);  
            return Array.newInstance(elementClass, 0).getClass();  
        } else {  
            return Class.forName(name, true, classLoader);  
        }  
    }  
  
    /** 
     * 解析原始数据类型 
     *  
     * @param name 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static Class resolvePrimitiveClassName(String name) {  
        if (name.length() <= 8) {  
            for (int i = 0; i < PRIMITIVE_CLASSES.length; i++) {  
                Class clazz = PRIMITIVE_CLASSES[i];  
                if (clazz.getName().equals(name))  
                    return clazz;  
            }  
        }  
        return null;  
    }  
  
  
  
    /** 
     * 获取一个类的ShortName 如：com.easyway.A 返回 A 
     *  
     * @param clazz 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static String getShortName(Class clazz) {  
        return getShortName(clazz.getName());  
    }  
  
    /** 
     * 判断一个类是否为内部类并获取一个类的ShortName 
     *  
     * @param className 
     * @return 
     */  
    public static String getShortName(String className) {  
        int lastDotIndex = className.lastIndexOf('.');  
        int nameEndIndex = className.indexOf("$$");  
        if (nameEndIndex == -1)  
            nameEndIndex = className.length();  
        String shortName = className.substring(lastDotIndex + 1, nameEndIndex);  
        shortName = shortName.replace('$', '.');  
        return shortName;  
    }  
  
    /** 
     * 获取一个方法所在类的全名 
     *  
     * @param method 
     *            方法名称 
     * @return 
     */  
    public static String getQualifiedMethodName(Method method) {  
        return (new StringBuilder()).append(  
                method.getDeclaringClass().getName()).append(".").append(  
                method.getName()).toString();  
    }  
  
    /** 
     * 根据类，方法名称和参数查找方法 
     *  
     * @param clazz 
     *            类名 
     * @param methodName 
     *            方法名称 
     * @param paramTypes 
     *            参数类型 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static boolean hasMethod(Class clazz, String methodName,  
            Class paramTypes[]) {  
        try {  
            clazz.getMethod(methodName, paramTypes);  
            return true;  
        } catch (NoSuchMethodException ex) {  
            return false;  
        }  
    }  
  
    /** 
     * 根据类和方法名返回方法的个数 
     *  
     * @param clazz 
     * @param methodName 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static int getMethodCountForName(Class clazz, String methodName) {  
        int count = 0;  
        do {  
            for (int i = 0; i < clazz.getDeclaredMethods().length; i++) {  
                Method method = clazz.getDeclaredMethods()[i];  
                if (methodName.equals(method.getName()))  
                    count++;  
            }  
            clazz = clazz.getSuperclass();  
        } while (clazz != null);  
        return count;  
    }  
  
    /** 
     *  
     * @param clazz 
     * @param methodName 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static boolean hasAtLeastOneMethodWithName(Class clazz,  
            String methodName) {  
        do {  
            for (int i = 0; i < clazz.getDeclaredMethods().length; i++) {  
                Method method = clazz.getDeclaredMethods()[i];  
                if (methodName.equals(method.getName()))  
                    return true;  
            }  
            clazz = clazz.getSuperclass();  
        } while (clazz != null);  
        return false;  
    }  
  
    /** 
     * 获取静态的方法的 
     *  
     * @param clazz 
     * @param methodName 
     * @param args 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static Method getStaticMethod(Class clazz, String methodName,  
            Class args[]) {  
        try {  
            Method method = clazz.getDeclaredMethod(methodName, args);  
            if ((method.getModifiers() & Modifier.STATIC) != 0)  
                return method;  
        } catch (NoSuchMethodException ex) {  
        }  
        return null;  
    }  
  
    @SuppressWarnings("unchecked")  
    public static String addResourcePathToPackagePath(Class clazz,  
            String resourceName) {  
        if (!resourceName.startsWith("/"))  
            return (new StringBuilder()).append(  
                    classPackageAsResourcePath(clazz)).append("/").append(  
                    resourceName).toString();  
        else  
            return (new StringBuilder()).append(  
                    classPackageAsResourcePath(clazz)).append(resourceName)  
                    .toString();  
    }  
  
    @SuppressWarnings("unchecked")  
    public static String classPackageAsResourcePath(Class clazz) {  
        if (clazz == null || clazz.getPackage() == null)  
            return "";  
        else  
            return clazz.getPackage().getName().replace('.', '/');  
    }  
  
    /** 
     * 根据对象获取所有的接口 
     *  
     * @param object 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static Class[] getAllInterfaces(Object object) {  
        Set interfaces = getAllInterfacesAsSet(object);  
        return (Class[]) (Class[]) interfaces.toArray(new Class[interfaces  
                .size()]);  
    }  
  
    /** 
     * 根据类获取所有的接口 
     *  
     * @param clazz 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static Class[] getAllInterfacesForClass(Class clazz) {  
        Set interfaces = getAllInterfacesForClassAsSet(clazz);  
        return (Class[]) (Class[]) interfaces.toArray(new Class[interfaces  
                .size()]);  
    }  
  
    /** 
     * 根据对象获取所有的接口 
     *  
     * @param object 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static Set getAllInterfacesAsSet(Object object) {  
        return getAllInterfacesForClassAsSet(object.getClass());  
    }  
  
    /** 
     * 根据类获取所有的接口 
     *  
     * @param clazz 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static Set getAllInterfacesForClassAsSet(Class clazz) {  
        Set interfaces = new HashSet();  
        for (; clazz != null; clazz = clazz.getSuperclass()) {  
            for (int i = 0; i < clazz.getInterfaces().length; i++) {  
                Class ifc = clazz.getInterfaces()[i];  
                interfaces.add(ifc);  
            }  
        }  
        return interfaces;  
    }  
  
    /** 
     * 检测一个方法或者一个属性是否为Public 修饰 
     *  
     * @param object 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static boolean isPublic(Class clazz, Member member) {  
        return Modifier.isPublic(member.getModifiers())  
                && Modifier.isPublic(clazz.getModifiers());  
    }  
  
    /** 
     * 检测一个Class是否为Abstract 修饰 
     *  
     * @param object 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static boolean isAbstractClass(Class clazz) {  
        int modifier = clazz.getModifiers();  
        return Modifier.isAbstract(modifier) || Modifier.isInterface(modifier);  
    }  
  
    /** 
     * 根据一个类获取一个默认的无参数的构造函数 
     *  
     * @param object 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static Constructor getDefaultConstructor(Class clazz) {  
        if (isAbstractClass(clazz))  
            return null;  
        try {  
            Constructor constructor = clazz  
                    .getDeclaredConstructor(EMPTY_CLASS_ARRAY);  
            if (!isPublic(clazz, constructor))  
                constructor.setAccessible(true);  
            return constructor;  
        } catch (NoSuchMethodException nme) {  
            return null;  
        }  
    }  
  
    /** 
     * 根据一个类和对应输入参数，获取一个对应参数的构造函数 
     *  
     * @param object 
     * @return 
     */  
    @SuppressWarnings("unchecked")  
    public static Constructor getConstructor(Class clazz,  
            Class parameterTypes[]) {  
        if (isAbstractClass(clazz))  
            return null;  
        try {  
            Constructor constructor = clazz.getConstructor(parameterTypes);  
            if (!isPublic(clazz, constructor))  
                constructor.setAccessible(true);  
            return constructor;  
        } catch (NoSuchMethodException nme) {  
            return null;  
        }  
    }  
  
    /** 
     * 将一个完整的类名装换为资源名称路径 
     *  
     * @param resourcePath 
     * @return 
     */  
    public static String convertResourcePathToClassName(String resourcePath) {  
        return resourcePath.replace('/', '.');  
    }  
  
    public static String convertClassNameToResourcePath(String className) {  
        return className.replace('.', '/');  
    }  
  
    /** 
     * 获取一个对象的属性 
     *  
     * @param <T> 
     * @param object 
     * @param propertyName 
     * @return 
     * @throws NoSuchFieldException 
     */  
    @SuppressWarnings("unchecked")  
    public static <T> T getDeclaredFieldValue(Object object, String propertyName)  
            throws NoSuchFieldException {  
        Field field = getDeclaredField(object.getClass(), propertyName);  
        boolean accessible = field.isAccessible();  
        Object result = null;  
        synchronized (field) {  
            field.setAccessible(true);  
            try {  
                result = field.get(object);  
            } catch (IllegalAccessException e) {  
                throw new NoSuchFieldException("No such field: "  
                        + object.getClass() + '.' + propertyName);  
            } finally {  
                field.setAccessible(accessible);  
            }  
        }  
        return (T) result;  
    }  
  
    /** 
     * 查找对应类的属性字段 
     *  
     * @param clazz 
     * @param propertyName 
     * @return 
     * @throws NoSuchFieldException 
     */  
    public static Field getDeclaredField(Class<?> clazz, String propertyName)  
            throws NoSuchFieldException {  
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass  
                .getSuperclass()) {  
  
            try {  
                return superClass.getDeclaredField(propertyName);  
            } catch (NoSuchFieldException e) {  
                // Field不在当前类定义,继续向上转型  
                e.printStackTrace();  
            }  
        }  
        throw new NoSuchFieldException("No such field: " + clazz.getName()  
                + '.' + propertyName);  
    }  
  
    /** 
     * 获取一个类的所有的属性 
     *  
     * @param clazz 
     * @return 
     */  
    public static Field[] getDeclaredFields(Class<?> clazz) {  
        Field[] fields = clazz.getDeclaredFields();  
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass  
                .getSuperclass()) {  
            fields = (Field[]) ArrayUtils.addAll(fields, superClass  
                    .getDeclaredFields());  
        }  
        return fields;  
    }  
  
    public static final String ARRAY_SUFFIX = "[]";  
    private static Class PRIMITIVE_CLASSES[];  
    private static final Class EMPTY_CLASS_ARRAY[] = new Class[0];  
    static {  
        PRIMITIVE_CLASSES = (new Class[] { Boolean.TYPE, Byte.TYPE,  
                Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE,  
                Float.TYPE, Double.TYPE });  
    }  
}