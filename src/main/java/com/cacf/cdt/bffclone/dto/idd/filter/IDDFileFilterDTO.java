package com.cacf.cdt.bffclone.dto.idd.filter;

import com.cacf.cdt.bffclone.dto.common.LocalDateRangeDTO;
import com.cacf.cdt.bffclone.dto.common.MinMaxDTO;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IDDFileFilterDTO {
    @Parameter
    private Set<String> agencies;
    @Parameter
    private Set<String> advisorNumbers;

    @Parameter
    private Set<String> productTypes;
    @Parameter
    private LocalDateRangeDTO enteredDate;
    @Parameter
    private MinMaxDTO<BigDecimal> amount;
    @Parameter
    private Set<String> enteredInputChannel;
    @Parameter
    private Set<String> egdCodes;
    @Parameter
    private Set<String> taskPriorities;
    @Parameter
    private Set<Integer> dlgNumbers;
    @Parameter
    private Set<String> enteredByAdvisorNumbers;
}
