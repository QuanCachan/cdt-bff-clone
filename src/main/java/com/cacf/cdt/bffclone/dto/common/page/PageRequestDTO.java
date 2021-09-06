package com.cacf.cdt.bffclone.dto.common.page;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Validated
@Schema(title = "Page request", description = "To request page of data")
public class PageRequestDTO {
    public static final int MAX_PAGE_SIZE = 100;
    public static final int DEFAULT_PAGE_SIZE = 10;

    @Parameter(description = "The page number starting from 0")
    @Min(0)
    private int pageNumber;

    @Parameter(description = "The size of the page")
    @Max(MAX_PAGE_SIZE)
    @Min(1)
    private int pageSize = DEFAULT_PAGE_SIZE;

    public PageRequestDTO nextPage() {
        return new PageRequestDTO(pageNumber + 1, pageSize);
    }
}
