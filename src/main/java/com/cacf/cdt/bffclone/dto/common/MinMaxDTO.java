package com.cacf.cdt.bffclone.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class MinMaxDTO<T extends Number> {
    private T min;
    private T max;
}
