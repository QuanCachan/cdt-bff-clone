package com.cacf.cdt.bffclone.dto.cdt;

import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskPriority;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTaskType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(title = "CDT Task", description = "Describe a user task linked to a file")
public class CDTTaskDTO {
    private CDTTaskPriority priority;
    private CDTTaskType type;
}
