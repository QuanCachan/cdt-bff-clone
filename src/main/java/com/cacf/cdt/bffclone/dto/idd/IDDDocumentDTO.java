package com.cacf.cdt.bffclone.dto.idd;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(title = "IDD Document", description = "Describe an attached document to an IDD File")
public class IDDDocumentDTO {
    private String code;
    private String status;
}