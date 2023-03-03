// Copyright (C) 2021 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import java.lang.annotation.Annotation;
import java.util.Set;

import org.reflections.Reflections;

import cn.pirrip.pip.BasePackage;
import lombok.experimental.UtilityClass;

/**
 * class 扫描工具
 *
 * @author gongshiwei@focusmedia.cn
 */
@UtilityClass
public class ReflectionsUtil {
    public static final Reflections REFLECTIONS = new Reflections(BasePackage.NAME);

    public static Set<Class<?>> getTypesAnnotatedWith(final Class<? extends Annotation> annotation) {
        return REFLECTIONS.getTypesAnnotatedWith(annotation);
    }
}
