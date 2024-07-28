package com.ittory.api.font.controller;

import com.ittory.api.font.dto.FontCreateRequest;
import com.ittory.api.font.dto.FontCreateResponse;
import com.ittory.api.font.dto.FontSearchResponse;
import com.ittory.api.font.usecase.FontUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/font")
@RequiredArgsConstructor
public class FontController {

    private final FontUseCase fontUseCase;

    @PostMapping
    public ResponseEntity<FontCreateResponse> createFont(@RequestBody FontCreateRequest request) {
        FontCreateResponse response = fontUseCase.createFont(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{fontId}")
    public ResponseEntity<FontSearchResponse> getFontById(@PathVariable("fontId") Long fontId) {
        FontSearchResponse response = fontUseCase.getFontById(fontId);
        return ResponseEntity.ok(response);
    }
}
