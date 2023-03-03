// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.model.validation.validator;

import javax.annotation.Nullable;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import cn.pirrip.pip.base.model.validation.EndsWith;

/**
 * ConstraintValidator for {@link EndsWith}
 *
 * @author Xiong Chao(xiongchao@focusmedia.cn)
 */
public class EndsWithConstraintValidator implements ConstraintValidator<EndsWith, CharSequence> {
    private EndsWith annotation;

    @Override
    public void initialize(EndsWith constraintAnnotation) {
        annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(@Nullable CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        String[] suffixes = annotation.value();

        if (annotation.ignoreCase()) {
            for (String suffix : suffixes) {
                if (StringUtils.endsWithIgnoreCase(value, suffix)) {
                    return true;
                }
            }

            return false;
        } else {
            return StringUtils.endsWithAny(value, suffixes);
        }
    }
}
