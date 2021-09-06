package com.cacf.cdt.bffclone.dto.idd;

import com.cacf.cdt.bffclone.dto.cdt.CDTUserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class IDDFileDetailsDTO extends IDDFileDTO {
    private Double amount;
    private String agency;

    private CDTUserDTO enteredBy;
    private CDTUserDTO underStudyBy;
    private CDTUserDTO supportedBy;
}
