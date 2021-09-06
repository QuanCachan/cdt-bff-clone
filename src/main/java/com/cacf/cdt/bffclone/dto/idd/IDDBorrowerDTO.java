package com.cacf.cdt.bffclone.dto.idd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IDDBorrowerDTO {
    private String number;
    private String firstName;
    private String lastName;
    private List<IDDBorrowerDocumentDTO> attachments;
    private List<IDDBorrowerDocumentDTO> documents;
}
