// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * provide a now Instant. make it possible to mock current time in unit tests.
 *
 * @author gongshiwei@focusmedia.cn
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NowProvider {
    @Getter
    @Setter(AccessLevel.PROTECTED)
    private static NowProvider instance = new NowProvider();

    protected Instant now() {
        return Instant.now();
    }
}
