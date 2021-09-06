package com.cacf.cdt.bffclone.entity.idd;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IDDBorrowerDocument {
    private String code;
    private String status;
}
