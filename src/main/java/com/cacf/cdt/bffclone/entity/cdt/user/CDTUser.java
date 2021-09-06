package com.cacf.cdt.bffclone.entity.cdt.user;

import lombok.Builder;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
public class CDTUser implements Serializable {
    @Id
    private String number;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CDTUserRole role;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String agency;
}
