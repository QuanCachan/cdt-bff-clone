package com.cacf.cdt.bffclone.dto.idd.filter;

import com.cacf.cdt.bffclone.dto.common.LocalDateRangeDTO;
import com.cacf.cdt.bffclone.dto.common.MinMaxDTO;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskPriority;
import com.cacf.cdt.bffclone.entity.idd.IDDEgdCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IDDFileFiltersDTO {
    private static final Set<IDDEgdCode> EGD_CODES = new TreeSet<>(List.of(IDDEgdCode.values()));
    private static final Set<CDTTaskPriority> TASK_PRIORITIES = new TreeSet<>(List.of(CDTTaskPriority.values()));

    private List<String> productTypes;
    private List<String> enteredInputChannels;
    private LocalDateRangeDTO enteredDate;
    private List<Integer> dlgNumbers;
    private MinMaxDTO<BigDecimal> amount;

    public Set<IDDEgdCode> getEgdCodes() {
        return EGD_CODES;
    }

    public Set<CDTTaskPriority> getTaskPriorities() {
        return TASK_PRIORITIES;
    }
}
