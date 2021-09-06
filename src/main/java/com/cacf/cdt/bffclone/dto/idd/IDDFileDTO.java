package com.cacf.cdt.bffclone.dto.idd;

import com.cacf.cdt.bffclone.dto.cdt.CDTTaskDTO;
import com.cacf.cdt.bffclone.dto.cdt.CDTUserDTO;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskPriority;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.cacf.cdt.bffclone.CdtBffCloneApplication.randomInList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IDDFileDTO {
    private String number;
    private IDDBorrowerDTO borrower;
    private IDDBorrowerDTO coBorrower;
    private String productType;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate enteredDate;
    private Double amount;
    private String enteredInputChannel;
    private String egdCode;
    private String teamFlowStatus;
    private Integer dlgNumber;
    private String agency;
    private CDTUserDTO enteredBy;
    private CDTTaskDTO task = CDTTaskDTO.builder()
            .priority(randomInList(List.of(CDTTaskPriority.values())))
            .type((randomInList(List.of(CDTTaskType.values()))))
            .build();
}
