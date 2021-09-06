package com.cacf.cdt.bffclone.controller;

import com.cacf.cdt.bffclone.dto.common.page.PageRequestDTO;
import com.cacf.cdt.bffclone.dto.common.page.PageResponseDTO;
import com.cacf.cdt.bffclone.dto.idd.IDDFileDTO;
import com.cacf.cdt.bffclone.dto.idd.IDDFileDetailsDTO;
import com.cacf.cdt.bffclone.dto.idd.filter.IDDFileFilterDTO;
import com.cacf.cdt.bffclone.dto.idd.filter.IDDFileFiltersDTO;
import com.cacf.cdt.bffclone.service.IDDFileService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/files")
@RequiredArgsConstructor
public class IDDFileController {
    private final IDDFileService iddFileService;

    @GetMapping("")
    PageResponseDTO<IDDFileDTO> findAll(@ParameterObject PageRequestDTO pageRequest, @ParameterObject IDDFileFilterDTO filter) {
        return iddFileService.findAll(pageRequest, filter);
    }

    @GetMapping("/filters")
    IDDFileFiltersDTO getFilters() {
        return iddFileService.getFilters();
    }

    @GetMapping("/{number}")
    IDDFileDetailsDTO findByNumber(@PathVariable("number") String number) {
        return iddFileService.findByNumber(number).orElse(null);
    }

}
