// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.common.base.Preconditions;

import lombok.experimental.UtilityClass;

/**
 * math utils
 *
 * @author ruicaihua@focusmedia.cn
 */
@UtilityClass
public class MathUtils {
    public static boolean less(BigDecimal a, BigDecimal b) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);

        return a.compareTo(b) < 0;
    }

    public static boolean greater(BigDecimal a, BigDecimal b) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);

        return a.compareTo(b) > 0;
    }

    public static boolean valueEqual(BigDecimal a, BigDecimal b) {
        Preconditions.checkNotNull(a);
        Preconditions.checkNotNull(b);

        return a.compareTo(b) == 0;
    }

    @SuppressWarnings("squid:S1244") // Floating point numbers should not be tested for equality
    public static boolean valueEqual(double doubleValue, int intValue) {
        // 直接使用 == 比较 双精度浮点数和整数.
        // 每个 int 都可以无损转为 double, 所以这个比较是安全的.
        return doubleValue == intValue;
    }

    public static boolean isPositive(BigDecimal a) {
        return greater(a, BigDecimal.ZERO);
    }

    public static boolean isNotPositive(BigDecimal a) {
        return !isPositive(a);
    }

    public static boolean isNotNegative(BigDecimal a) {
        return greaterOrEqual(a, BigDecimal.ZERO);
    }

    public static boolean greaterOrEqual(BigDecimal a, BigDecimal b) {
        return !less(a, b);
    }

    public static double getAverage(int[] array) {
        Preconditions.checkNotNull(array);
        int length = array.length;
        Preconditions.checkArgument(length > 0, "empty array");
        double sum = 0;
        for (int e : array) {
            sum += e;
        }
        return sum / length;
    }

    /**
     * 被除数是否能被除数整除
     *
     * @param number  被除数
     * @param divisor 除数
     * @return 被除数是否能被除数整除
     */
    public static boolean isDivisible(BigDecimal number, BigDecimal divisor) {
        BigDecimal multiplicand = number.divide(divisor, 0, RoundingMode.DOWN);
        return valueEqual(number, divisor.multiply(multiplicand));
    }

    public static BigDecimal nullAsZero(BigDecimal value) {
        return value == null ? BigDecimal.ZERO : value;
    }
}
