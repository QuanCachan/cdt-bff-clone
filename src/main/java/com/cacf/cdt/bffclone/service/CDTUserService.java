package com.cacf.cdt.bffclone.service;

import com.cacf.cdt.bffclone.dto.cdt.CDTUserDTO;
import com.cacf.cdt.bffclone.mapper.CDTUserMapper;
import com.cacf.cdt.bffclone.repository.CDTUserRepository;
import com.cacf.cdt.bffclone.repository.vo.TupleVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CDTUserService {
    private final CDTUserRepository cdtUserRepository;
    private final CDTUserMapper cdtUserMapper;

    /**
     * Find all agencies from files
     *
     * @return A {@link Map} with agency region as key and agencies name into that region as value
     */
    public Mono<Map<String, Collection<String>>> findAllAgencies() {
        return Flux.fromIterable(cdtUserRepository.findAgencies())
                .collectMultimap(TupleVO::getT1, TupleVO::getT2);
    }

    /**
     * Find all users of the given agency with advisor role
     *
     * @param agency The advisors agency
     * @return The advisors of the given agency or empty
     */
    public Flux<CDTUserDTO> findAllAdvisorsByAgency(String agency) {
        return Flux.fromIterable(cdtUserRepository.findAllAdvisorsByAgency(agency)).map(cdtUserMapper::toDTO);
    }
}
