package com.ittory.api.letter.controller;

import com.ittory.api.letter.dto.FontCreateRequest;
import com.ittory.api.letter.dto.FontCreateResponse;
import com.ittory.api.letter.dto.FontSearchResponse;
import com.ittory.api.letter.service.FontService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/font")
@RequiredArgsConstructor
public class FontController {

    private final FontService fontService;

    @Operation(summary = "폰트 생성", description = "사용자가 폰트를 생성합니다.")
    @PostMapping
    public ResponseEntity<FontCreateResponse> createFont(@RequestBody FontCreateRequest request) {
        FontCreateResponse response = fontService.createFont(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "특정 폰트 조회", description = "폰트 ID로 폰트를 조회합니다.")
    @GetMapping("/{fontId}")
    public ResponseEntity<FontSearchResponse> getFontById(@PathVariable("fontId") Long fontId) {
        FontSearchResponse response = fontService.getFont(fontId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "폰트 모두 조회", description = "생성 순서대로 오름차순 정렬.")
    @GetMapping("/all")
    public ResponseEntity<List<FontSearchResponse>> getAllFont() {
        List<FontSearchResponse> response = fontService.getAllFonts();
        return ResponseEntity.ok().body(response);
    }

}
