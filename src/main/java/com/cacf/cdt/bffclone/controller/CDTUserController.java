package com.cacf.cdt.bffclone.controller;

import com.cacf.cdt.bffclone.dto.cdt.CDTUserDTO;
import com.cacf.cdt.bffclone.service.CDTUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/")
@RequiredArgsConstructor
public class CDTUserController {

    private final CDTUserService cdtUserService;

    @GetMapping("/agencies")
    Map<String, List<String>> findAllAgencies() {
        return cdtUserService.findAllAgencies();
    }

    @GetMapping("/agencies/{agency}/advisors")
    List<CDTUserDTO> findAlladvisorsByAgency(@PathVariable("agency") String agency) {
        return cdtUserService.findAllAdvisorsByAgency(agency);
    }
}
