package com.cacf.cdt.bffclone.mapper;

import com.cacf.cdt.bffclone.dto.idd.IDDFileDetailsDTO;
import com.cacf.cdt.bffclone.dto.idd.IDDFileSummaryDTO;
import com.cacf.cdt.bffclone.entity.idd.IDDFile;
import org.mapstruct.Mapper;

@Mapper
public interface IDDFileMapper {
    IDDFileSummaryDTO toIDDFileDTO(IDDFile vo);

    IDDFileDetailsDTO toIDDFileDetailsDTO(IDDFile vo);
}
