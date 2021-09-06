package com.cacf.cdt.bffclone.mapper;

import com.cacf.cdt.bffclone.dto.cdt.CDTUserDTO;
import com.cacf.cdt.bffclone.entity.cdt.user.CDTUser;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CDTUserMapper {
    List<CDTUserDTO> toDTOs(List<CDTUser> user);
}
