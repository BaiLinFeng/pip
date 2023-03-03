// Copyright (C) 2020 Focus Media Holding Ltd. All Rights Reserved.

package cn.pirrip.pip.base.util.date;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * BasicDate
 *
 * @author likaiqiang@focusmedia.cn
 */
@Data
@NoArgsConstructor
public class BasicDate implements BaseDate {

    private LocalDate startDate;

    private LocalDate endDate;

    public BasicDate(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
