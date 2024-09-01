package com.ittory.api.letter.controller;

import com.ittory.api.letter.dto.FontCreateRequest;
import com.ittory.api.letter.dto.FontCreateResponse;
import com.ittory.api.letter.dto.FontSearchResponse;
import com.ittory.api.letter.usecase.FontCreateUseCase;
import com.ittory.api.letter.usecase.FontReadUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
