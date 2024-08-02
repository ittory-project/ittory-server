package com.ittory.api.font.controller;

import com.ittory.api.font.dto.FontCreateRequest;
import com.ittory.api.font.dto.FontCreateResponse;
import com.ittory.api.font.dto.FontSearchResponse;
import com.ittory.api.font.usecase.FontCreateUseCase;
import com.ittory.api.font.usecase.FontReadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/font")
@RequiredArgsConstructor
public class FontController {

    private final FontCreateUseCase fontCreateUseCase;
    private final FontReadUseCase fontReadUseCase;

    @PostMapping
    public ResponseEntity<FontCreateResponse> createFont(@RequestBody FontCreateRequest request) {
        FontCreateResponse response = fontCreateUseCase.execute(request);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{fontId}")
    public ResponseEntity<FontSearchResponse> getFontById(@PathVariable("fontId") Long fontId) {
        FontSearchResponse response = fontReadUseCase.execute(fontId);
        return ResponseEntity.ok().body(response);
    }
}
