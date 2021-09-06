package com.cacf.cdt.bffclone.mapper;

import com.cacf.cdt.bffclone.dto.common.MinMaxDTO;
import com.cacf.cdt.bffclone.repository.vo.MinMaxVO;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
public class MinMaxMapper {
    public <T extends Number> MinMaxDTO<T> toDTO(MinMaxVO<T> vo) {
        if (vo == null) {
            return null;
        }
        val minMax = new MinMaxDTO<T>();
        minMax.setMin(vo.getMin());
        minMax.setMax(vo.getMax());
        return minMax;
    }
}
