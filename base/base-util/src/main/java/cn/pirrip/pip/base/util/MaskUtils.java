// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import static com.google.common.base.Preconditions.checkArgument;

import org.apache.commons.lang3.StringUtils;

import lombok.experimental.UtilityClass;

/**
 * Utils for masking string.
 *
 * @author Xiong Chao(xiongchao@focusmedia.cn)
 */
@UtilityClass
public class MaskUtils {
    private static final String MASK_CHAR = "*";
    private static final String AT_SIGN = "@";
    private static final String MASKED_CREDENTIAL = "[PROTECTED]";

    public static String maskPassword() {
        return MASKED_CREDENTIAL;
    }

    public static String maskPhone(String phone) {
        return maskString(phone, 3, 2);
    }

    public static String maskEmail(String email) {
        if (StringUtils.isBlank(email)) {
            return "";
        }

        String[] parts = StringUtils.split(email, AT_SIGN, 2);
        if (parts.length < 2) {
            return email;
        }
        return maskString(parts[0], 2, 1) + AT_SIGN + parts[1];
    }

    /**
     * mask string with default mask char.
     *
     * @param str       the str need mask
     * @param leftSkip  skip char count from left hand side
     * @param rightSkip skip char count from right hand side
     * @return the masked str
     */
    public static String maskString(String str, int leftSkip, int rightSkip) {
        checkArgument(leftSkip >= 0, "leftSkip(%s) must not be negative.", leftSkip);
        checkArgument(rightSkip >= 0, "rightSkip(%s) must not be negative.", rightSkip);

        if (StringUtils.isBlank(str)) {
            return "";
        }

        int len = str.length();
        int start = leftSkip > len ? len - 1 : leftSkip;
        int end = rightSkip > len ? 0 : len - rightSkip;
        int maskLength = end - start;

        if (maskLength <= 0) {
            return str;
        }

        String mask = StringUtils.repeat(MASK_CHAR, maskLength);
        return StringUtils.overlay(str, mask, start, end);
    }
}
