package com.ittory.api.letter.controller;

import com.ittory.api.letter.dto.CoverTypeCreateRequest;
import com.ittory.api.letter.dto.CoverTypeCreateResponse;
import com.ittory.api.letter.dto.CoverTypeSearchResponse;
import com.ittory.api.letter.service.CoverTypeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cover-type")
@RequiredArgsConstructor
public class CoverTypeController {

    private final CoverTypeService coverTypeService;

    @Operation(summary = "커버타입 생성", description = "사용자가 커버타입을 생성합니다.")
    @PostMapping
    public ResponseEntity<CoverTypeCreateResponse> createCoverType(@RequestBody CoverTypeCreateRequest request) {
        CoverTypeCreateResponse response = coverTypeService.createCoverType(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "특정 커버타입 조회", description = "커버타입 ID로 커버타입을 조회합니다.")
    @GetMapping("/{coverTypeId}")
    public ResponseEntity<CoverTypeSearchResponse> getCoverTypeById(@PathVariable("coverTypeId") Long coverTypeId) {
        CoverTypeSearchResponse response = coverTypeService.getCoverType(coverTypeId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "커버타입 모두 조회", description = "ACTIVE 상태의 커버타입만, 생성 순서대로 오름차순 정렬.")
    @GetMapping("/all")
    public ResponseEntity<List<CoverTypeSearchResponse>> getAllActiveCoverType() {
        List<CoverTypeSearchResponse> response = coverTypeService.getAllActiveCoverType();
        return ResponseEntity.ok().body(response);
    }
}
