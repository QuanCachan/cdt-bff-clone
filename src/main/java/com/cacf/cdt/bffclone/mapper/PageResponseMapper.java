package com.cacf.cdt.bffclone.mapper;

import com.cacf.cdt.bffclone.dto.common.page.PageResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageResponseMapper {

    public <T> PageResponseDTO<T> mapInto(PageResponseDTO<T> target, Page<T> source) {
        if (target == null) {
            return null;
        }
        target.setContent(source.getContent());
        target.setNumber(source.getNumber());
        target.setSize(source.getSize());
        target.setTotalElements(source.getTotalElements());
        target.setTotalPages(source.getTotalPages());
        return target;
    }

    public <T> PageResponseDTO<T> toDTO(Page<T> page) {
        return mapInto(new PageResponseDTO<>(), page);
    }

}
