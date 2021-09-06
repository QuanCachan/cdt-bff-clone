package com.cacf.cdt.bffclone.dto.idd;

import com.cacf.cdt.bffclone.dto.cdt.CDTTaskDTO;
import com.cacf.cdt.bffclone.dto.cdt.CDTUserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "IDD File Summary", description = "Describe a summary of an IDD File")
public class IDDFileSummaryDTO {
    private String number;
    private IDDBorrowerDTO borrower;
    private IDDBorrowerDTO coBorrower;
    private List<IDDDocumentDTO> documents;
    private String productType;
    private String subscriptionMode;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate enteredDate;
    private Double amount;
    private String egdCode;
    private String teamFlowStatus;
    private Integer dlgNumber;
    private String agency;
    private CDTUserDTO enteredBy;
    private CDTTaskDTO task;
}
