package com.cacf.cdt.bffclone.entity.cdt.task;

import com.cacf.cdt.bffclone.entity.cdt.task.action.CDTTaskAction;
import com.cacf.cdt.bffclone.entity.cdt.user.CDTUser;
import com.cacf.cdt.bffclone.entity.idd.IDDFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

import static javax.persistence.ConstraintMode.NO_CONSTRAINT;

@Table(name = "cdt_tasks")
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
public class CDTTask {
    @Id
    @Column(name = "iddFileId")
    private String id;

    @Enumerated(EnumType.ORDINAL)
    private CDTTaskPriority priority;
    @Enumerated(EnumType.STRING)
    private CDTTaskType type;
    @Enumerated(EnumType.STRING)
    private CDTTaskCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "affectedTo", referencedColumnName = "number", foreignKey = @ForeignKey(NO_CONSTRAINT))
    private CDTUser affectedTo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "iddFileId")
    private IDDFile file;

    @OneToMany(fetch = FetchType.LAZY)
    private List<CDTTaskAction> actions;
}
