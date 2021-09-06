package com.cacf.cdt.bffclone.dto.cdt;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(title = "CDT User", description = "Describe a user of CDT")
public class CDTUserDTO {
    private String number;
    private String firstName;
    private String lastName;
}
