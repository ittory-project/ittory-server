package com.ittory.api.letter.controller;

import com.ittory.api.letter.dto.ElementImageResponse;
import com.ittory.api.letter.dto.AllElementsResponse;
import com.ittory.api.letter.service.ElementService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "편지의 현재 요소 현황 조회", description = "(Authenticated) \n" +
            "1. startedAt, memberId, nickname, content : null -> 작성 전 Element \n" +
            "2. content : null -> 작성 중인 Element \n" +
            "3. 모두 채워졌을 경우 -> 작성 완료된 Element")
    @GetMapping("/current/{letterId}")
    public ResponseEntity<AllElementsResponse> getCurrentElements(@PathVariable Long letterId) {
        AllElementsResponse response = elementService.getCurrentElements(letterId);
        return ResponseEntity.ok().body(response);
    }
}
