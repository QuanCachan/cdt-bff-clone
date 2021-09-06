package com.cacf.cdt.bffclone.mapper;

import com.cacf.cdt.bffclone.dto.common.LocalDateRangeDTO;
import com.cacf.cdt.bffclone.repository.vo.DateRangeVO;
import lombok.val;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateRangeMapper {
    public LocalDateRangeDTO toDTO(DateRangeVO<LocalDate> vo) {
        if (vo == null) {
            return null;
        }
        val minMax = new LocalDateRangeDTO();
        minMax.setFrom(vo.getFrom());
        minMax.setTo(vo.getTo());
        return minMax;
    }
}
