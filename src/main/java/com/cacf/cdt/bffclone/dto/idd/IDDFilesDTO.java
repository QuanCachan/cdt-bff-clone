package com.cacf.cdt.bffclone.dto.idd;

import com.cacf.cdt.bffclone.dto.common.page.PageResponseDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
@JsonPropertyOrder("totalAmount")
public class IDDFilesDTO extends PageResponseDTO<IDDFileDTO> {
    private BigDecimal totalAmount;
}
