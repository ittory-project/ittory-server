package com.ittory.api.letter.controller;

import com.ittory.api.letter.dto.CoverTypeCreateRequest;
import com.ittory.api.letter.dto.CoverTypeCreateResponse;
import com.ittory.api.letter.dto.CoverTypeSearchResponse;
import com.ittory.api.letter.usecase.CoverTypeCreateUseCase;
import com.ittory.api.letter.usecase.CoverTypeReadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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