package com.cacf.cdt.bffclone.dto.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Schema(title = "Date range", description = "To define a range of locale date")
public class LocalDateRangeDTO {
    public static final String FORMAT = "yyyy-MM-dd";
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = FORMAT)
    @Parameter(name = "From date", schema = @Schema(type = "string", format = "date", pattern = LocalDateRangeDTO.FORMAT))
    private LocalDate from;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = FORMAT)
    @Parameter(name = "To date", schema = @Schema(type = "string", format = "date", pattern = LocalDateRangeDTO.FORMAT))
    private LocalDate to;
}
