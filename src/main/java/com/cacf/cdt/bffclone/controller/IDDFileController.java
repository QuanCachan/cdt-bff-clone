package com.cacf.cdt.bffclone.controller;

import com.cacf.cdt.bffclone.dto.common.page.PageRequestDTO;
import com.cacf.cdt.bffclone.dto.common.page.PageResponseDTO;
import com.cacf.cdt.bffclone.dto.idd.IDDFileDetailsDTO;
import com.cacf.cdt.bffclone.dto.idd.IDDFileSummaryDTO;
import com.cacf.cdt.bffclone.dto.idd.filter.IDDFileFilterDTO;
import com.cacf.cdt.bffclone.dto.idd.filter.IDDFileFiltersDTO;
import com.cacf.cdt.bffclone.service.IDDFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/files")
@RequiredArgsConstructor
@Tag(name = "IDD Files", description = "IDD Files API")
public class IDDFileController {
    private final IDDFileService iddFileService;

    @GetMapping("")
    @Operation(
            summary = "Find files matching filters",
            description = "Find all files matching the given filter parameters. Files are sorted by entered date ascending and priority descending",
            responses = @ApiResponse(description = "A page containing matching files")
    )
    PageResponseDTO<IDDFileSummaryDTO> findAll(@Valid @ParameterObject PageRequestDTO pageRequest, @Valid @ParameterObject IDDFileFilterDTO filter) {
        return iddFileService.findAllOrderedByEnteredDateAndPriority(pageRequest, filter);
    }

    @GetMapping("/filters")
    @Operation(
            summary = "Get pre-defined filters",
            description = "Get pre-defined filters that can be used to filter files",
            responses = @ApiResponse(description = "Pre-defined filters")
    )
    Mono<IDDFileFiltersDTO> getFilters() {
        return iddFileService.getFilters();
    }

    @GetMapping("/{number}")
    @Operation(
            summary = "Get a file",
            description = "Get a file given its unique number",
            responses = @ApiResponse(description = "The file")
    )
    Mono<IDDFileDetailsDTO> findByNumber(@PathVariable("number") String number) {
        return iddFileService.findByNumber(number);
    }

}
