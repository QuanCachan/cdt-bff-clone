package com.cacf.cdt.bffclone.controller;

import com.cacf.cdt.bffclone.dto.cdt.CDTUserDTO;
import com.cacf.cdt.bffclone.service.CDTUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping(path = "/")
@RequiredArgsConstructor
@Tag(name = "CDT Users", description = "CDT Users API")
public class CDTUserController {

    private final CDTUserService cdtUserService;

    @GetMapping("/agencies")
    @Operation(
            summary = "All agencies",
            description = "Find all agencies by region",
            responses = @ApiResponse(description = "A json describing agencies by region as {[region]:[...agencies]}")
    )
    Mono<Map<String, Collection<String>>> findAllAgencies() {
        return cdtUserService.findAllAgencies();
    }

    @GetMapping("/agencies/{agency}/advisors")
    @Operation(
            summary = "All advisors",
            description = "Find all advisors given the region",
            responses = @ApiResponse(
                    description = "A array of json containing all users with the role advisor given the agency"
            )
    )
    @ResponseStatus(HttpStatus.OK)
    Flux<CDTUserDTO> findAlladvisorsByAgency(@PathVariable("agency") String agency) {
        return cdtUserService.findAllAdvisorsByAgency(agency);
    }
}
