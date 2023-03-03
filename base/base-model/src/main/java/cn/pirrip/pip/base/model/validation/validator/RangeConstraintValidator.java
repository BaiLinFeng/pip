// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.model.validation.validator;

import static com.google.common.base.Preconditions.checkState;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nonnull;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import cn.pirrip.pip.base.model.validation.ValidRange;
import lombok.val;

/**
 * ConstraintValidator for ValidRange
 *
 * @author gongshiwei@focusmedia.cn
 */
public class RangeConstraintValidator implements ConstraintValidator<ValidRange, Object> {
    private static final LoadingCache<Class<?>, Map<String, PropertyDescriptor>> DESCRIPTOR_CACHE;

    static {
        val cacheLoader = new CacheLoader<Class<?>, Map<String, PropertyDescriptor>>() {
            @Override
            public Map<String, PropertyDescriptor> load(@Nonnull Class<?> clazz) throws IntrospectionException {
                BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
                return Stream.of(beanInfo.getPropertyDescriptors())
                        .collect(Collectors.toMap(PropertyDescriptor::getName, Function.identity()));
            }
        };
        DESCRIPTOR_CACHE = CacheBuilder.newBuilder().build(cacheLoader);
    }

    private String minFieldName;
    private String maxFieldName;

    /**
     * 调用getter方法读取bean的属性.
     *
     * <p>
     * spring的{@link org.springframework.beans.BeanUtils#getPropertyDescriptor(Class, String)}方法提供了更好的实现,
     * 但是model模块没有spring环境, 所以造了一个简单的轮子.
     * </p>
     */
    @SuppressWarnings("unchecked")
    private static <T> T getProperty(final Object bean, final String property) {
        Class<?> beanClass = bean.getClass();
        try {
            Map<String, PropertyDescriptor> descriptorMap = DESCRIPTOR_CACHE.get(beanClass);
            PropertyDescriptor propertyDescriptor = descriptorMap.get(property);
            checkState(propertyDescriptor != null, "no such property %s of type %s", property, beanClass);
            Method readMethod = propertyDescriptor.getReadMethod();
            checkState(readMethod != null, "no read method for property %s of type %s", property, beanClass);
            return (T) readMethod.invoke(bean);
        } catch (IllegalAccessException | ExecutionException | InvocationTargetException e) {
            return ExceptionUtils.wrapAndThrow(e);
        }
    }

    @Override
    public void initialize(ValidRange constraintAnnotation) {
        minFieldName = constraintAnnotation.minProperty();
        maxFieldName = constraintAnnotation.maxProperty();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext validatorContext) {
        final Comparable<Object> minValue = getProperty(value, minFieldName);
        final Comparable<Object> maxValue = getProperty(value, maxFieldName);
        if (minValue != null && maxValue != null && minValue.compareTo(maxValue) > 0) {
            HibernateConstraintValidatorContext context = (HibernateConstraintValidatorContext) validatorContext;
            context.addMessageParameter("minProperty", minFieldName);
            context.addMessageParameter("maxProperty", maxFieldName);
            context.addMessageParameter("min", minValue);
            context.addMessageParameter("max", maxValue);
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addConstraintViolation();
            return false;
        } else {
            return true;
        }
    }
}
