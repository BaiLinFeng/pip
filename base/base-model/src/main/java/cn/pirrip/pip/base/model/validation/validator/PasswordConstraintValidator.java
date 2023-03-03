// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.model.validation.validator;

import java.util.List;

import javax.annotation.Nullable;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.passay.CharacterCharacteristicsRule;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.Rule;

import com.google.common.collect.ImmutableList;

import cn.pirrip.pip.base.model.validation.Password;

/**
 * ConstraintValidator for {@link Password}
 *
 * @author Xiong Chao(xiongchao@focusmedia.cn)
 */
public class PasswordConstraintValidator implements ConstraintValidator<Password, CharSequence> {
    private static final List<Rule> RULES = ImmutableList.of(
            new LengthRule(8, 16),
            new CharacterCharacteristicsRule(
                    2,
                    new CharacterRule(EnglishCharacterData.Alphabetical),
                    new CharacterRule(EnglishCharacterData.Digit),
                    new CharacterRule(EnglishCharacterData.Special))
    );

    @Override
    public boolean isValid(@Nullable CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return new PasswordValidator(RULES).validate(new PasswordData(value.toString())).isValid();
    }
}
