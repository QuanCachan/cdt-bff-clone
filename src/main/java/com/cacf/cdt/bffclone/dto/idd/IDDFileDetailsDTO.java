package com.cacf.cdt.bffclone.dto.idd;

import com.cacf.cdt.bffclone.dto.cdt.CDTUserDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "IDD File Details", description = "Describe all details of an IDD File")
public class IDDFileDetailsDTO extends IDDFileSummaryDTO {
    private String agency;

    private Double amount;
    private Boolean ade;
    private String subProductType;
    private String enteredInputChannel;

    private CDTUserDTO enteredBy;
    private CDTUserDTO underStudyBy;
    private CDTUserDTO supportedBy;
}
