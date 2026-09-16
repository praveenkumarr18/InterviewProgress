package com.interviewcompass.controller;

import com.interviewcompass.dto.CompanyApplicationRequest;
import com.interviewcompass.dto.CompanyApplicationResponse;
import com.interviewcompass.security.AuthenticatedUser;
import com.interviewcompass.service.CompanyApplicationService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
public class CompanyApplicationController {

    private final CompanyApplicationService companyApplicationService;

    public CompanyApplicationController(CompanyApplicationService companyApplicationService) {
        this.companyApplicationService = companyApplicationService;
    }

    @PostMapping
    public ResponseEntity<CompanyApplicationResponse> createCompanyApplication(
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser,
            @Valid @RequestBody CompanyApplicationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyApplicationService.createCompanyApplication(currentUser.getUserId(), request));
    }

    @GetMapping
    public ResponseEntity<List<CompanyApplicationResponse>> getCompanyApplications(
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(companyApplicationService.getCompanyApplications(currentUser.getUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyApplicationResponse> getCompanyApplicationById(
            @PathVariable Long id,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        return ResponseEntity.ok(companyApplicationService.getCompanyApplicationById(id, currentUser.getUserId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyApplicationResponse> updateCompanyApplication(
            @PathVariable Long id,
            @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser,
            @Valid @RequestBody CompanyApplicationRequest request) {
        return ResponseEntity.ok(companyApplicationService.updateCompanyApplication(id, currentUser.getUserId(), request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompanyApplication(@PathVariable Long id,
                                                         @org.springframework.security.core.annotation.AuthenticationPrincipal AuthenticatedUser currentUser) {
        companyApplicationService.deleteCompanyApplication(id, currentUser.getUserId());
        return ResponseEntity.noContent().build();
    }
}
