package com.cacf.cdt.bffclone.entity.cdt.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Table(name = "cdt_users")
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "number")
@Builder
@AllArgsConstructor
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
