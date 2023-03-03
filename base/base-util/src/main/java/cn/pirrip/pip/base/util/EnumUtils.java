// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import java.util.Optional;

import lombok.experimental.UtilityClass;

/**
 * EnumUtils
 *
 * @author Zhang Hang(zhanghang@focusmedia.cn)
 */
@UtilityClass
public class EnumUtils {
    /**
     * 根据value得到对应的枚举对象
     *
     * @param type  枚举类型
     * @param value value
     * @param <V>   type of value
     * @param <T>   type of enum extends HaveValueEnum
     * @return 对应的枚举对象
     */
    public static <V, T extends Enum<T> & HaveValueEnum<V>> Optional<T> valueOf(Class<T> type, V value) {
        for (T enumConstant : type.getEnumConstants()) {
            if (enumConstant.getValue().equals(value)) {
                return Optional.of(enumConstant);
            }
        }
        return Optional.empty();
    }
}
