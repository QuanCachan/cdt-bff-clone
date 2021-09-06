package com.cacf.cdt.bffclone.dto.cdt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CDTUserDTO {
    private String number;
    private String firstName;
    private String lastName;
}
