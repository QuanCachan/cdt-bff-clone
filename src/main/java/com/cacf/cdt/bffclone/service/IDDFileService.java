package com.cacf.cdt.bffclone.service;

import com.cacf.cdt.bffclone.dto.common.page.PageRequestDTO;
import com.cacf.cdt.bffclone.dto.idd.IDDFileDetailsDTO;
import com.cacf.cdt.bffclone.dto.idd.IDDFileSummaryDTO;
import com.cacf.cdt.bffclone.dto.idd.IDDFilesPageResponseDTO;
import com.cacf.cdt.bffclone.dto.idd.filter.IDDFileFilterDTO;
import com.cacf.cdt.bffclone.dto.idd.filter.IDDFileFiltersDTO;
import com.cacf.cdt.bffclone.entity.cdt.task.CDTTask_;
import com.cacf.cdt.bffclone.entity.idd.IDDFile_;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    /**
     * Find all files matching the given filter
     *
     * @param pageRequest The page to request
     * @param filter      The files filter
     * @return A page of matching files
     * with the total amount cumulated
     * ordered by entered date ascending and priority descending
     */
    public IDDFilesPageResponseDTO findAllOrderedByEnteredDateAndPriority(PageRequestDTO pageRequest, IDDFileFilterDTO filter) {
        val spec = new IDDFileSpecification(filter);
        Page<IDDFileSummaryDTO> iddFiles = iddFileRepository.findAll(
                        spec,
                        PageRequest.of(
                                Optional.of(pageRequest).map(PageRequestDTO::getPageNumber).filter(p -> p >= 0).orElse(0),
                                Optional.of(pageRequest).map(PageRequestDTO::getPageSize)
                                        .filter(p -> p > 0 && p <= PageRequestDTO.MAX_PAGE_SIZE).orElse(PageRequestDTO.DEFAULT_PAGE_SIZE),
                                Sort.by(Sort.Order.asc(IDDFile_.ENTERED_DATE), Sort.Order.desc(IDDFile_.TASK + "." + CDTTask_.PRIORITY))
                        ))
                .map(iddFileMapper::toIDDFileDTO);
        IDDFilesPageResponseDTO filesDTO = new IDDFilesPageResponseDTO();
        if (!iddFiles.isEmpty()) {
            filesDTO.setTotalAmount(iddFileAdvancedRepository.sumAmount(spec));
        }
        pageResponseMapper.mapInto(filesDTO, iddFiles);
        return filesDTO;
    }

    /**
     * Find a file by its unique number
     *
     * @param number The file number to find
     * @return The file or empty if not found
     */
    public Mono<IDDFileDetailsDTO> findByNumber(String number) {
        return Mono.justOrEmpty(iddFileRepository.findByNumber(number))
                .map(iddFileMapper::toIDDFileDetailsDTO);
    }

    /**
     * Build pre-defined filters from files in database
     *
     * @return Pre-defined file filters
     */
    public Mono<IDDFileFiltersDTO> getFilters() {
        return Mono.just(IDDFileFiltersDTO.builder())
                .flatMap(filters -> Flux.concat(
                                Mono.fromSupplier(() -> filters.productTypes(iddFileRepository.findAllProductTypes())),
                                Mono.fromSupplier(() -> filters.enteredDate(dateRangeMapper.toDTO(iddFileRepository.findEnteredDateRange()))),
                                Mono.fromSupplier(() -> filters.dlgNumbers(iddFileRepository.findAllDlgNumbers())),
                                Mono.fromSupplier(() -> filters.amount(minMaxMapper.toDTO(iddFileRepository.findMinMaxAmount())))
                        ).last()
                ).map(IDDFileFiltersDTO.IDDFileFiltersDTOBuilder::build);
    }
}
