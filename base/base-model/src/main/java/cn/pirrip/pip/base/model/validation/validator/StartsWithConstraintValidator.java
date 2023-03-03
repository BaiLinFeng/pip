// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.model.validation.validator;

import javax.annotation.Nullable;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import cn.pirrip.pip.base.model.validation.StartsWith;


/**
 * ConstraintValidator for {@link StartsWith}
 *
 * @author Xiong Chao(xiongchao@focusmedia.cn)
 */
public class StartsWithConstraintValidator implements ConstraintValidator<StartsWith, CharSequence> {
    private StartsWith annotation;

    @Override
    public void initialize(StartsWith constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(@Nullable CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String[] prefixes = annotation.value();

        if (annotation.ignoreCase()) {
            for (String prefix : prefixes) {
                if (StringUtils.startsWithIgnoreCase(value, prefix)) {
                    return true;
                }
            }

            return false;
        } else {
            return StringUtils.startsWithAny(value, prefixes);
        }
    }
}
