package com.cacf.cdt.bffclone.dto.cdt;

import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskPriority;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CDTTaskDTO {
    private CDTTaskPriority priority;
    private CDTTaskType type;
}
