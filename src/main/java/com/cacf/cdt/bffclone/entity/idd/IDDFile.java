package com.cacf.cdt.bffclone.entity.idd;

import com.cacf.cdt.bffclone.entity.cdt.task.CDTTask;
import com.cacf.cdt.bffclone.entity.cdt.user.CDTUser;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.ConstraintMode.NO_CONSTRAINT;

@TypeDef(name = "json", typeClass = JsonType.class)
@Table(name = "idd_files")
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "number")
@Builder
@AllArgsConstructor
public class IDDFile {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private String id;

    @NotBlank
    @Column(unique = true)
    private String number;

    @NotNull
    private LocalDateTime receivedDate;

    @Embedded
    private IDDBorrower borrower;

    @Embedded
    private IDDBorrower coBorrower;

    private String teamFlowStatus;

    private Integer dlgNumber;

    @Enumerated(EnumType.STRING)
    private IDDEgdCode egdCode;

    private String productType;
    private String subProductType;
    private Boolean ade;

    private BigDecimal amount;
    private String agency;
    private String agencyRegion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advisor", referencedColumnName = "number", foreignKey = @ForeignKey(NO_CONSTRAINT))
    private CDTUser advisor;

    private LocalDate enteredDate;
    private String enteredInputChannel;
    private String subscriptionMode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enteredBy", referencedColumnName = "number", foreignKey = @ForeignKey(NO_CONSTRAINT))
    private CDTUser enteredBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "underStudyBy", referencedColumnName = "number", foreignKey = @ForeignKey(NO_CONSTRAINT))
    private CDTUser underStudyBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supportedBy", referencedColumnName = "number", foreignKey = @ForeignKey(NO_CONSTRAINT))
    private CDTUser supportedBy;

    @OneToOne(mappedBy = "file", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private CDTTask task;

    @Type(type = "json")
    @Column(columnDefinition = "json")
    private CollectionWrapper<IDDDocument> documents = new CollectionWrapper<>();
}
