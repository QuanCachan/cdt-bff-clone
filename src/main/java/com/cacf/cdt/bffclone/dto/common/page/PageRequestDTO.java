package com.cacf.cdt.bffclone.dto.common.page;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PageRequestDTO {
    @Parameter
    private int pageNumber;
    @Parameter
    private int pageSize;

    public PageRequestDTO nextPage() {
        return new PageRequestDTO(pageNumber + 1, pageSize);
    }
}
