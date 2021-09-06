package com.cacf.cdt.bffclone.dto.common.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@Schema(title = "Page response", description = "Response to a requested page")
public class PageResponseDTO<T> {
    private int totalPages;
    private long totalElements;
    private int number;
    private int size;
    private List<T> content;
}
