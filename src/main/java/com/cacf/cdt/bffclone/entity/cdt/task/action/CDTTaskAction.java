package com.cacf.cdt.bffclone.entity.cdt.task.action;

import com.cacf.cdt.bffclone.entity.cdt.user.CDTUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "cdt_task_actions")
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
public class CDTTaskAction {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    private String id;

    @Enumerated(EnumType.STRING)
    private CDTTaskActionType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private CDTTaskAction task;

    @ManyToOne(fetch = FetchType.LAZY)
    private CDTUser doneBy;

    private LocalDateTime doneAt;
}
