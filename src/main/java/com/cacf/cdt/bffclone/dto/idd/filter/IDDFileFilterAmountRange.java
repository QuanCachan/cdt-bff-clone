package com.cacf.cdt.bffclone.dto.idd.filter;

import com.cacf.cdt.bffclone.dto.common.MinMaxDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(title = "IDD file filter amount range", description = "To filter files given min and/or max amount")
public class IDDFileFilterAmountRange extends MinMaxDTO<BigDecimal> {
}
