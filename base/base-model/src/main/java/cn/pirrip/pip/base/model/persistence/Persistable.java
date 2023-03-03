// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.model.persistence;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.common.annotations.Beta;

/**
 * 配合 {@linkplain PersistAsJson} 使用, 声明某个字段类型需要以 JSON 格式持久化.
 *
 * @author gongshiwei@focusmedia.cn
 * @see PersistAsJson
 */
@Beta // 暂时别用, 待完善功能
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Persistable {
}
