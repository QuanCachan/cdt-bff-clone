package com.cacf.cdt.bffclone.dto.idd.filter;

import com.cacf.cdt.bffclone.dto.common.LocalDateRangeDTO;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskPriority;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskType;
import com.cacf.cdt.bffclone.entity.idd.IDDEgdCode;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(title = "IDD file filters", description = "To filter files request")
public class IDDFileFilterDTO {
    @Parameter(description = "Agency name", examples = @ExampleObject("Roubaix,Paris"))
    private Set<String> agencies;
    @Parameter(description = "Advisor registration number attached to the file by IDD", examples = @ExampleObject("S00001,S00002"))
    private Set<String> advisors;

    @Parameter(description = "Type of product", examples = @ExampleObject("CR,PB,RAC"))
    private Set<String> productTypes;

    private LocalDateRangeDTO enteredDate;
    private IDDFileFilterAmountRange amount;

    @Parameter(description = "Kind of subscription mode", examples = @ExampleObject("SE,PAPIER"))
    private Set<String> subscriptionModes;
    @Parameter(description = "EGD code", array = @ArraySchema(schema = @Schema(enumAsRef = true, implementation = IDDEgdCode.class)))
    private Set<String> egdCodes;
    @Parameter(description = "Delegation number")
    private Set<Integer> dlgNumbers;
    @Parameter(description = "Files entered by those advisor numbers")
    private Set<String> enteredBy;
    @Parameter(description = "Task priority", array = @ArraySchema(schema = @Schema(enumAsRef = true, implementation = CDTTaskPriority.class)))
    private Set<String> taskPriorities;
    @Parameter(description = "Task type", array = @ArraySchema(schema = @Schema(enumAsRef = true, implementation = CDTTaskType.class)))
    private Set<String> taskTypes;
    @Parameter(description = "Advisor registration number linked to the task by Teamflow")
    private Set<String> taskAffectedTo;
}
