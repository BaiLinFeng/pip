// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.model.validation;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import cn.pirrip.pip.base.model.validation.validator.StartsWithConstraintValidator;

/**
 * The annotated {@code CharSequence} must start with any of specific prefix(es).
 *
 * <p>
 * Accepts {@code CharSequence}. {@code null} elements are considered valid.
 *
 * @author Xiong Chao(xiongchao@focusmedia.cn)
 */
@Documented
@Constraint(validatedBy = {StartsWithConstraintValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface StartsWith {
    String[] value();

    boolean ignoreCase() default false;

    String message() default "'${validatedValue}' must start with: '{value}'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
