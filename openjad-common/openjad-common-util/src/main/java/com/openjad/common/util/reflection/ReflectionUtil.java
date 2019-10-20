package com.openjad.common.util.reflection;

import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.util.Assert;

import static com.openjad.common.constant.CharacterConstants.*;
import com.openjad.common.util.StringUtils;
import com.openjad.logger.api.Logger;
import com.openjad.logger.api.LoggerFactory;

/**
 * 
 * @author hechuan
 *
 */
@SuppressWarnings("rawtypes")
public class ReflectionUtil {

	private static final String SETTER_PREFIX = "set";

	private static final String GETTER_PREFIX = "get";

	private static final String CGLIB_CLASS_SEPARATOR = "$$";

	private static final String IS = "is";

	private static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

	/**
	 * 
	 * @param obj p
	 * @param propertyName p
	 * @return p
	 */
	public static Object invokeGetter(Object obj, String propertyName) {
		Object object = obj;
		for (String name : StringUtils.split(propertyName, PERIOD_CHARACTER)) {
			String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
			object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
		}
		return object;
	}

	/**
	 * 
	 * @param obj p
	 * @param propertyName p
	 * @param value p
	 */
	public static void invokeSetter(Object obj, String propertyName, Object value) {
		Object object = obj;
		String[] names = StringUtils.split(propertyName, PERIOD_CHARACTER);
		for (int i = 0; i < names.length; i++) {
			if (i < names.length - 1) {
				String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(names[i]);
				object = invokeMethod(object, getterMethodName, new Class[] {}, new Object[] {});
			} else {
				String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(names[i]);
				invokeMethodByName(object, setterMethodName, new Object[] { value });
			}
		}
	}

	/**
	 * 
	 * @param obj p
	 * @param fieldName p
	 * @return p
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("在目标对象[" + obj + "]中找不到属性 [" + fieldName + "] ");
		}

		Object result = null;
		try {
			result = field.get(obj);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常," + e.getMessage(), e);
		}
		return result;
	}

	/**
	 * 
	 * @param obj p
	 * @param fieldName p
	 * @param value p
	 */ 
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {

		Assert.notNull(obj, "object 不能为空");
		Assert.notNull(fieldName, "fieldName 不能为空");

		Field field = getAccessibleField(obj, fieldName);

		if (field == null) {
			throw new IllegalArgumentException("找不到属性 [" + fieldName + "] 目标类型:[" + obj.getClass().getName() + "]");
		}

		if (value != null) {
			if (!field.getType().isAssignableFrom(value.getClass())) {
				throw new IllegalArgumentException("类型不兼容，不能尝试把一个类型为[" + value.getClass().getName()
						+ "]的值赋给类型为[" + field.getType() + "]的属性,object:" + obj.getClass().getName());
			}
		}
		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			logger.error("不可能抛出的异常," + e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param obj p
	 * @param methodName p
	 * @param parameterTypes p
	 * @param args p
	 * @return p
	 */
	public static Object invokeMethod(final Object obj, final String methodName,
			final Class<?>[] parameterTypes, final Object[] args) {

		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("在目标对象[" + obj + "]中找不到方法 [" + methodName + "]");
		}
		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw new ReflectionException(e);
		}
	}

	/**
	 * 
	 * @param obj p
	 * @param methodName p
	 * @param args p
	 * @return p
	 */
	public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
		Method method = getAccessibleMethodByName(obj, methodName);
		if (method == null) {
			throw new IllegalArgumentException("在目标对象[" + obj + "]中找不到方法 [" + methodName + "]");
		}
		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw new ReflectionException(e);
		}
	}

	/**
	 * 
	 * @param obj p
	 * @param fieldName p
	 * @return p
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		Assert.notNull(obj, "object 不能为空");
		Assert.notNull(fieldName, "fieldName 不能为空");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
				return field;
			} catch (NoSuchFieldException e) {
				continue;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param clazz p
	 * @return p
	 */
	public static Set<Field> getClassFields(Class<?> clazz) {
		Set<Field> result = new LinkedHashSet<Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			if (Modifier.isTransient(field.getModifiers())) {
				continue;
			}
			result.add(field);
		}

		Class<?> superClass = clazz.getSuperclass();
		if (superClass.equals(Object.class)) {
			return result;
		}
		result.addAll(getClassFields(superClass));
		return result;
	}

	public static <T extends Annotation> boolean hasAnnotation(Method method, Class<T> annotationClass) {
		return (method.getAnnotation(annotationClass)) != null ? true : false;
	}

	public static <T extends Annotation> boolean hasAnnotation(Field field, Class<T> annotationClass) {
		return (field.getAnnotation(annotationClass)) != null ? true : false;
	}

	//	

	/**
	 * 
	 * @param obj p
	 * @param methodName p
	 * @param parameterTypes p
	 * @return p
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {

		Assert.notNull(obj, "object 不能为空");
		Assert.notNull(methodName, "methodName 不能为空");

		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
			try {
				Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
				makeAccessible(method);
				return method;
			} catch (NoSuchMethodException e) {
				// Method不在当前类定义,继续向上转型
				continue;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param obj p
	 * @param methodName p
	 * @return p
	 */
	public static Method getAccessibleMethodByName(final Object obj, final String methodName) {
		Assert.notNull(obj, "object 不能为空");
		Assert.notNull(methodName, "methodName 不能为空");

		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType.getSuperclass()) {
			Method[] methods = searchType.getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().equals(methodName)) {
					makeAccessible(method);
					return method;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @param method p
	 */
	public static void makeAccessible(Method method) {
		boolean isNotPub = !Modifier.isPublic(method.getModifiers())
				|| !Modifier.isPublic(method.getDeclaringClass().getModifiers());
		if (isNotPub && !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * 
	 * @param field p
	 */
	public static void makeAccessible(Field field) {
		boolean isNotPub = !Modifier.isPublic(field.getModifiers())
				|| !Modifier.isPublic(field.getDeclaringClass().getModifiers())
				|| Modifier.isFinal(field.getModifiers());
		if (isNotPub && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	public static Class getSuperClassGenricType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		return getGenricType(genType, clazz, index);
	}

	/**
	 * 
	 * @param clazz p
	 * @param index p
	 * @return p
	 */
	public static Class getSuperInterfaceGenricType(final Class clazz, final int index) {
		Type[] genTypes = clazz.getGenericInterfaces();
		if (genTypes == null || genTypes.length == 0) {
			logger.warn(clazz.getName() + " 没有实现接口 ");
			return Object.class;
		}
		return getGenricType(genTypes[0], clazz, index);
	}

	/**
	 * 
	 * @param clazz p
	 * @return p
	 */
	public static Class getSuperInterfaceGenricType(final Class clazz) {
		return getSuperInterfaceGenricType(clazz, 0);
	}

	/**
	 * 
	 * @param genType p
	 * @param clazz p
	 * @param index p
	 * @return p
	 */
	private static Class getGenricType(Type genType, final Class clazz, final int index) {
		if (!(genType instanceof ParameterizedType)) {
			logger.warn(clazz.getName() + " 的父类不是参数化类型 ");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			logger.warn("Index: " + index + ", Size of " + clazz.getName()
					+ "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			logger.warn(clazz.getName() + " 未对超类泛型参数设置实际类 ");
			return Object.class;
		}
		return (Class) params[index];
	}

	/**
	 * 
	 * @param instance p
	 * @return p
	 */
	public static Class<?> getUserClass(Object instance) {
		if (instance == null) {
			throw new IllegalArgumentException("Instance 不能为空");
		}
		Class clazz = instance.getClass();
		if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;

	}

	/**
	 * 查找对你的getter方法
	 * 
	 * @param containerClass p
	 * @param propertyName p
	 * @return p
	 */
	public static Method findGetterMethod(Class containerClass, String propertyName) {
		Class checkClass = containerClass;
		Method getter = null;

		while (getter == null && checkClass != null) {
			if (checkClass.equals(Object.class)) {
				break;
			}
			getter = getGetterOrNull(checkClass, propertyName);
			checkClass = checkClass.getSuperclass();
		}

		if (getter == null) {
			for (Class theInterface : containerClass.getInterfaces()) {
				getter = getGetterOrNull(theInterface, propertyName);
				if (getter != null) {
					break;
				}
			}
		}

		if (getter == null) {
			String err = String.format(Locale.ROOT, "没有找到属性  [%s#%s] 对应的get方法",
					containerClass.getName(), propertyName);
			logger.warn(err);
			throw new ReflectionException(err);
		}

		getter.setAccessible(true);
		return getter;
	}

	/**
	 * 获得对像的setter方法
	 * 
	 * @param containerClass p
	 * @param propertyName p
	 * @param propertyType p
	 * @return p
	 */
	public static Method findSetterMethod(Class containerClass, String propertyName, Class propertyType) {
		Class checkClass = containerClass;
		Method setter = null;

		while (setter == null && checkClass != null) {
			if (checkClass.equals(Object.class)) {
				break;
			}

			setter = setterOrNull(checkClass, propertyName, propertyType);
			checkClass = checkClass.getSuperclass();
		}

		if (setter == null) {
			for (Class theInterface : containerClass.getInterfaces()) {
				setter = setterOrNull(theInterface, propertyName, propertyType);
				if (setter != null) {
					break;
				}
			}
		}

		if (setter == null) {
			String err = String.format(Locale.ROOT, "没有找到属性 [%s#%s]对应的set方法",
					containerClass.getName(), propertyName);
			logger.warn(err);
			throw new ReflectionException(err);
		}

		setter.setAccessible(true);
		return setter;
	}

	/**
	 * 
	 * @param cls p
	 * @return p
	 */
	public static boolean isPrimitives(Class<?> cls) {
		if (cls.isArray()) {
			return isPrimitive(cls.getComponentType());
		}
		return isPrimitive(cls);
	}

	/**
	 * 
	 * @param cls p
	 * @return p
	 */
	public static boolean isPrimitive(Class<?> cls) {
		return cls.isPrimitive() || cls == String.class || cls == Boolean.class || cls == Character.class
				|| Number.class.isAssignableFrom(cls) || Date.class.isAssignableFrom(cls);
	}

	/**
	 * 
	 * @param c p
	 * @return p
	 */
	public static Class<?> getBoxedClass(Class<?> c) {
		if (c == int.class) {
			c = Integer.class;
		} else if (c == boolean.class) {
			c = Boolean.class;
		} else if (c == long.class) {
			c = Long.class;
		} else if (c == float.class) {
			c = Float.class;
		} else if (c == double.class) {
			c = Double.class;
		} else if (c == char.class) {
			c = Character.class;
		} else if (c == byte.class) {
			c = Byte.class;
		} else if (c == short.class) {
			c = Short.class;
		}
		return c;
	}

	/**
	 * is compatible.
	 *
	 * @param c
	 *            class.
	 * @param o
	 *            instance.
	 * @return compatible or not.
	 */
	public static boolean isCompatible(Class<?> c, Object o) {
		boolean pt = c.isPrimitive();
		if (o == null) {
			return !pt;
		}

		if (pt) {
			if (c == int.class) {
				c = Integer.class;
			} else if (c == boolean.class) {
				c = Boolean.class;
			} else if (c == long.class) {
				c = Long.class;
			} else if (c == float.class) {
				c = Float.class;
			} else if (c == double.class) {
				c = Double.class;
			} else if (c == char.class) {
				c = Character.class;
			} else if (c == byte.class) {
				c = Byte.class;
			} else if (c == short.class) {
				c = Short.class;
			}
		}
		if (c == o.getClass()) {
			return true;
		}
		return c.isInstance(o);
	}

	/**
	 * is compatible.
	 *
	 * @param cs
	 *            class array.
	 * @param os
	 *            object array.
	 * @return compatible or not.
	 */
	public static boolean isCompatible(Class<?>[] cs, Object[] os) {
		int len = cs.length;
		if (len != os.length) {
			return false;
		}

		if (len == 0) {
			return true;
		}

		for (int i = 0; i < len; i++) {
			if (!isCompatible(cs[i], os[i])) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 
	 * @param containerClass p
	 * @param propertyName p
	 * @return p
	 */
	private static Method getGetterOrNull(Class containerClass, String propertyName) {
		for (Method method : containerClass.getDeclaredMethods()) {
			if (method.getParameterTypes().length != 0) {
				continue;
			}
			if (method.isBridge()) {
				continue;
			}

			final String methodName = method.getName();
			
			if (methodName.startsWith(GETTER_PREFIX)) {
				final String stemName = methodName.substring(3);
				final String decapitalizedStemName = Introspector.decapitalize(stemName);
				if (stemName.equals(propertyName) || decapitalizedStemName.equals(propertyName)) {
					verifyNoIsVariantExists(containerClass, propertyName, method, stemName);
					return method;
				}

			}

			if (methodName.startsWith(IS)) {
				final String stemName = methodName.substring(2);
				String decapitalizedStemName = Introspector.decapitalize(stemName);
				if (stemName.equals(propertyName) || decapitalizedStemName.equals(propertyName)) {
					verifyNoGetVariantExists(containerClass, propertyName, method, stemName);
					return method;
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * @param containerClass p
	 * @param propertyName p
	 * @param isMethod p
	 * @param stemName p
	 */
	@SuppressWarnings("unchecked")
	private static void verifyNoGetVariantExists(Class containerClass,String propertyName,
			Method isMethod,String stemName) {
		try {
			final Method getMethod = containerClass.getDeclaredMethod(GETTER_PREFIX + stemName);
			checkGetAndIsVariants(containerClass, propertyName, getMethod, isMethod);
		} catch (NoSuchMethodException ignore) {
		}
	}

	/**
	 * 
	 * @param containerClass p
	 * @param propertyName p
	 * @param getMethod p
	 * @param stemName p
	 */
	@SuppressWarnings("unchecked")
	private static void verifyNoIsVariantExists(Class containerClass,String propertyName,
			Method getMethod,String stemName) {
		try {
			final Method isMethod = containerClass.getDeclaredMethod(IS + stemName);
			checkGetAndIsVariants(containerClass, propertyName, getMethod, isMethod);
		} catch (NoSuchMethodException ignore) {
		}
	}

	/**
	 * 
	 * @param containerClass p
	 * @param propertyName p
	 * @param getMethod p
	 * @param isMethod p
	 */
	private static void checkGetAndIsVariants(
			Class containerClass, String propertyName,
			Method getMethod, Method isMethod) {
		if (!isMethod.getReturnType().equals(getMethod.getReturnType())) {
			String err = "尝试获取类[%s]的属性[%s]的get方法时，没有找到对应的get[%s]或is[%s]方法";
			err = String.format(Locale.ROOT, err,containerClass.getName(), propertyName,
					getMethod.toString(), isMethod.toString());
			throw new ReflectionException(err);
		}
	}

	/**
	 * 
	 * @param theClass p
	 * @param propertyName p
	 * @param propertyType p
	 * @return p
	 */
	private static Method setterOrNull(Class theClass, String propertyName, Class propertyType) {
		Method potentialSetter = null;
		for (Method method : theClass.getDeclaredMethods()) {
			final String methodName = method.getName();
			if (method.getParameterTypes().length == 1 && methodName.startsWith(SETTER_PREFIX)) {
				final String testOldMethod = methodName.substring(3);
				final String testStdMethod = Introspector.decapitalize(testOldMethod);
				if (testStdMethod.equals(propertyName) || testOldMethod.equals(propertyName)) {
					potentialSetter = method;
					if (propertyType == null || method.getParameterTypes()[0].equals(propertyType)) {
						break;
					}
				}
			}
		}
		return potentialSetter;
	}

}
