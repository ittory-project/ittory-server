package com.ittory.api.letter.controller;

import com.ittory.api.letter.dto.ElementImageResponse;
import com.ittory.api.letter.service.ElementService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/element")
@RequiredArgsConstructor
public class ElementController {

    private final ElementService elementService;

    @Operation(summary = "요소의 이미지 조회", description = "(Authenticated) 편지 Id와 요소 순번으로 요소의 이미지 조회.")
    @GetMapping("/image")
    public ResponseEntity<ElementImageResponse> getElementImage(@RequestParam Long letterId,
                                                                @RequestParam Integer sequence) {
        ElementImageResponse response = elementService.getElementImage(letterId, sequence);
        return ResponseEntity.ok().body(response);
    }
}
