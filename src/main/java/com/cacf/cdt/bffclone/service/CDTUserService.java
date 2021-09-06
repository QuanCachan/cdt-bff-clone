package com.cacf.cdt.bffclone.service;

import com.cacf.cdt.bffclone.dto.cdt.CDTUserDTO;
import com.cacf.cdt.bffclone.mapper.CDTUserMapper;
import com.cacf.cdt.bffclone.repository.CDTUserRepository;
import com.cacf.cdt.bffclone.repository.vo.TupleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CDTUserService {
    private final CDTUserRepository cdtUserRepository;
    private final CDTUserMapper cdtUserMapper;

    public Map<String, List<String>> findAllAgencies() {
        return cdtUserRepository.findAgencies()
                .stream()
                .collect(groupingBy(TupleVO::getT1, mapping(TupleVO::getT2, toList())));
    }

    public List<CDTUserDTO> findAllAdvisorsByAgency(String agency) {
        return cdtUserMapper.toDTOs(cdtUserRepository.findAllAdvisorsByAgency(agency));
    }
}
