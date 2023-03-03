// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;


import static org.apache.commons.lang3.exception.ExceptionUtils.rethrow;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import lombok.experimental.UtilityClass;
import net.sf.cglib.beans.BeanCopier;

/**
 * BeanCopyUtils
 *
 * @author gongshiwei
 */
@UtilityClass
public class BeanCopyUtils {
    private static LoadingCache<Class<?>, BeanCopier> beanCopierCache = CacheBuilder.newBuilder()
            .build(CacheLoader.from(beanClass -> BeanCopier.create(beanClass, beanClass, false)));

    /**
     * 浅拷贝一个java bean
     */
    public static <T> T copyOf(T bean) {
        if (bean == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        Class<T> beanClass = (Class<T>) bean.getClass();
        try {
            T copy = instantiateClass(beanClass);
            BeanCopier beanCopier = beanCopierCache.get(beanClass);
            beanCopier.copy(bean, copy, null);
            return copy;
        } catch (ExecutionException e) {
            return rethrow(e);
        }
    }

    private static <T> T instantiateClass(Class<T> clazz) {
        if (clazz.isInterface()) {
            throw new IllegalArgumentException("Specified class[" + clazz.getName() + "] is an interface");
        }
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No default constructor for class[" + clazz.getName() + "] found", e);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new UndeclaredThrowableException(e, "Constructor class[" + clazz.getName() + "] threw exception");
        }
    }
}
