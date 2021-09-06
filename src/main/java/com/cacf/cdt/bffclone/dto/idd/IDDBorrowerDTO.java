package com.cacf.cdt.bffclone.dto.idd;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(title = "IDD Borrower", description = "Describe a borrower or co-borrower of an IDD File")
public class IDDBorrowerDTO {
    private String number;
    private String firstName;
    private String lastName;
    private List<IDDDocumentDTO> attachments;
}
