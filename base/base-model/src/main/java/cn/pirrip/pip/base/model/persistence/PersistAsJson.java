// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.model.persistence;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 声明被标注的类型需要用 json 格式持久化到数据库.
 *
 * <p> 也可以标注字段. 标注字段时要求字段所属的类有 {@linkplain Persistable} 标注. 这个特性在处理来自其他模块的类型时很有用.
 *
 * @author gongshiwei@focusmedia.cn
 * @see Persistable
 * @see cn.pirrip.pip.base.server.database.typehandler.BaseJsonTypeHandler
 */
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PersistAsJson {
    // TODO support JPA?
    // TODO support gzip?
}
