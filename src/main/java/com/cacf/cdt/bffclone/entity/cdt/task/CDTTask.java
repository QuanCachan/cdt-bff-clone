package com.cacf.cdt.bffclone.entity.cdt.task;

import com.cacf.cdt.bffclone.entity.cdt.user.CDTUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.ConstraintMode.NO_CONSTRAINT;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
public class CDTTask {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private String id;

    private CDTTaskPriority priority;
    private CDTTaskType type;
    private CDTTaskCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affectedTo", referencedColumnName = "number", foreignKey = @ForeignKey(NO_CONSTRAINT))
    private CDTUser affectedTo;
}
