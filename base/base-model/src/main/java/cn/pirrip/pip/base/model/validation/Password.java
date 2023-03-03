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

import cn.pirrip.pip.base.model.validation.validator.PasswordConstraintValidator;

/**
 * The annotated {@code CharSequence} must be a valid password.
 *
 * <p>
 * Accepts {@code CharSequence}. {@code null} is considered valid.
 *
 * @author Xiong Chao(xiongchao@focusmedia.cn)
 */
@Documented
@Constraint(validatedBy = {PasswordConstraintValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Password {
    String message() default "Invalid Password Format.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
