package com.cacf.cdt.bffclone.dto.idd;

import com.cacf.cdt.bffclone.dto.cdt.CDTUserDTO;
import com.cacf.cdt.bffclone.dto.common.page.PageResponseDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@JsonPropertyOrder("totalAmount")
@Schema(
        title = "IDD files page",
        description = "IDD files page response with the cumulated total amount and list of all advisors that entered a file"
)
public class IDDFilesPageResponseDTO extends PageResponseDTO<IDDFileSummaryDTO> {
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private List<CDTUserDTO> enteredByFilter;
}
