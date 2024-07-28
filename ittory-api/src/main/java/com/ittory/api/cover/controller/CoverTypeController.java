package com.ittory.api.cover.controller;

import com.ittory.api.cover.dto.CoverTypeCreateRequest;
import com.ittory.api.cover.dto.CoverTypeCreateResponse;
import com.ittory.api.cover.dto.CoverTypeSearchResponse;
import com.ittory.api.cover.usecase.CoverTypeUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/cover-type")
@RequiredArgsConstructor
public class CoverTypeController {

    private final CoverTypeUseCase coverTypeUseCase;

    @PostMapping
    public ResponseEntity<CoverTypeCreateResponse> createCoverType(@RequestBody CoverTypeCreateRequest request) {
        CoverTypeCreateResponse response = coverTypeUseCase.createCoverType(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{coverTypeId}")
    public ResponseEntity<CoverTypeSearchResponse> getCoverTypeById(@PathVariable("coverTypeId") Long coverTypeId) {
        CoverTypeSearchResponse response = coverTypeUseCase.getCoverTypeById(coverTypeId);
        return ResponseEntity.ok(response);
    }
}
