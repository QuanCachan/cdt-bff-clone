package com.cacf.cdt.bffclone.dto.idd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IDDBorrowerDocumentDTO {
    private String code;
    private String status;
}