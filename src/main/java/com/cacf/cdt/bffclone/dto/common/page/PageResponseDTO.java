package com.cacf.cdt.bffclone.dto.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
public class PageResponseDTO<T> {
    private int totalPages;
    private long totalElements;
    private int number;
    private int size;
    private List<T> content;
}
