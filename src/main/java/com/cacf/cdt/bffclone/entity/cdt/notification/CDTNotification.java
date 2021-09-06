package com.cacf.cdt.bffclone.entity.cdt.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Table(name = "cdt_notifications")
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
public class CDTNotification {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private String id;

    @NotBlank
    private String cdtUserNumber;
    @NotBlank
    private String iddFileNumber;

    @NotNull
    @Builder.Default
    private LocalDate createdAt = LocalDate.now();
    private LocalDate seenAt;
}
