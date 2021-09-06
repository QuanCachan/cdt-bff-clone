package com.cacf.cdt.bffclone.service;

import com.cacf.cdt.bffclone.dto.common.page.PageRequestDTO;
import com.cacf.cdt.bffclone.dto.idd.IDDFileDTO;
import com.cacf.cdt.bffclone.dto.idd.IDDFileDetailsDTO;
import com.cacf.cdt.bffclone.dto.idd.IDDFilesDTO;
import com.cacf.cdt.bffclone.dto.idd.filter.IDDFileFilterDTO;
import com.cacf.cdt.bffclone.dto.idd.filter.IDDFileFiltersDTO;
import com.cacf.cdt.bffclone.mapper.DateRangeMapper;
import com.cacf.cdt.bffclone.mapper.IDDFileMapper;
import com.cacf.cdt.bffclone.mapper.MinMaxMapper;
import com.cacf.cdt.bffclone.mapper.PageResponseMapper;
import com.cacf.cdt.bffclone.repository.IDDFileAdvancedRepository;
import com.cacf.cdt.bffclone.repository.IDDFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IDDFileService {
    private final IDDFileRepository iddFileRepository;
    private final IDDFileAdvancedRepository iddFileAdvancedRepository;
    private final IDDFileMapper iddFileMapper;
    private final PageResponseMapper pageResponseMapper;
    private final MinMaxMapper minMaxMapper;
    private final DateRangeMapper dateRangeMapper;

    public IDDFilesDTO findAll(PageRequestDTO pageRequest, IDDFileFilterDTO filter) {
        val spec = new IDDFileSpecification(filter);
        Page<IDDFileDTO> iddFiles = iddFileRepository.findAll(
                        spec,
                        PageRequest.of(
                                Optional.of(pageRequest).map(PageRequestDTO::getPageNumber).filter(p -> p > 0).orElse(0),
                                Optional.of(pageRequest).map(PageRequestDTO::getPageSize).filter(p -> p > 0 && p <= 100).orElse(10)
                        ))
                .map(iddFileMapper::toIDDFileDTO);
        IDDFilesDTO filesDTO = new IDDFilesDTO();
        if (!iddFiles.isEmpty()) {
            filesDTO.setTotalAmount(iddFileAdvancedRepository.sumAmount(spec));
        }
        pageResponseMapper.mapInto(filesDTO, iddFiles);
        return filesDTO;
    }


    public Optional<IDDFileDetailsDTO> findByNumber(String number) {
        return iddFileRepository.findByNumber(number)
                .map(iddFileMapper::toIDDFileDetailsDTO);
    }

    public IDDFileFiltersDTO getFilters() {
        val filters = new IDDFileFiltersDTO();
        filters.setProductTypes(iddFileRepository.findAllProductTypes());
        filters.setEnteredInputChannels(iddFileRepository.findAllEnteredInputChannels());
        filters.setEnteredDate(dateRangeMapper.toDTO(iddFileRepository.findEnteredDateRange()));
        filters.setDlgNumbers(iddFileRepository.findAllDlgNumbers());
        filters.setAmount(minMaxMapper.toDTO(iddFileRepository.findMinMaxAmount()));
        return filters;
    }
}
