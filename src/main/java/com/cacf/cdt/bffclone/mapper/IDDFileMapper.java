package com.cacf.cdt.bffclone.mapper;

import com.cacf.cdt.bffclone.dto.idd.IDDFileDTO;
import com.cacf.cdt.bffclone.dto.idd.IDDFileDetailsDTO;
import com.cacf.cdt.bffclone.entity.idd.IDDFile;
import org.mapstruct.Mapper;

@Mapper
public interface IDDFileMapper {
    IDDFileDTO toIDDFileDTO(IDDFile vo);

    IDDFileDetailsDTO toIDDFileDetailsDTO(IDDFile vo);
}
