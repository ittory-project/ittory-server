package com.ittory.api.cover.controller;

import com.ittory.api.cover.dto.CoverTypeCreateRequest;
import com.ittory.api.cover.dto.CoverTypeCreateResponse;
import com.ittory.api.cover.dto.CoverTypeSearchResponse;
import com.ittory.api.cover.usecase.CoverTypeCreateUseCase;
import com.ittory.api.cover.usecase.CoverTypeReadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cover-type")
@RequiredArgsConstructor
public class CoverTypeController {

    private final CoverTypeCreateUseCase coverTypeCreateUseCase;
    private final CoverTypeReadUseCase coverTypeReadUseCase;

    @PostMapping
    public ResponseEntity<CoverTypeCreateResponse> createCoverType(@RequestBody CoverTypeCreateRequest request) {
        CoverTypeCreateResponse response = coverTypeCreateUseCase.execute(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{coverTypeId}")
    public ResponseEntity<CoverTypeSearchResponse> getCoverTypeById(@PathVariable("coverTypeId") Long coverTypeId) {
        CoverTypeSearchResponse response = coverTypeReadUseCase.execute(coverTypeId);
        return ResponseEntity.ok().body(response);
    }
}
