package com.cacf.cdt.bffclone.dto.common;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "Value range", description = "To define a range of values given min and/or max")
public class MinMaxDTO<T extends Number> {
    @Parameter(description = "Value min")
    private T min;
    @Parameter(description = "Value max")
    private T max;
}
