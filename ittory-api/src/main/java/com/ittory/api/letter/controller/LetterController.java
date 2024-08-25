package com.ittory.api.letter.controller;

import com.ittory.api.letter.dto.*;
import com.ittory.api.letter.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/letter")
@RequiredArgsConstructor
public class LetterController {

    private final LetterCreateUseCase letterCreateUseCase;
    private final LetterDeleteUseCase letterDeleteUseCase;
    private final LetterElementUseCase letterElementUseCase;
    private final LetterRepeatCountUseCase letterRepeatCountUseCase;
    private final LetterInfoUseCase letterInfoUseCase;
    private final LetterDetailUseCase letterDetailUseCase;

    @PostMapping
    public ResponseEntity<LetterCreateResponse> createLetter(@RequestBody LetterCreateRequest request) {
        LetterCreateResponse response = letterCreateUseCase.execute(request);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/repeat-count")
    public ResponseEntity<Void> registerRepeatCount(@RequestBody LetterRepeatCountRequest request) {
        letterRepeatCountUseCase.execute(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/element/{letterElementId}")
    public ResponseEntity<LetterElementResponse> registerLetterElementContent(@PathVariable Long letterElementId, @RequestBody LetterElementRequest request) {
        LetterElementResponse response = letterElementUseCase.execute(letterElementId, request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{letterId}")
    public ResponseEntity<Void> deleteLetter(@PathVariable Long letterId) {
        letterDeleteUseCase.execute(letterId);
        return ResponseEntity.noContent().build();
    }

    // 편지 기본 정보 조회 (대기실)
    @GetMapping("/info/{letterId}")
    public ResponseEntity<LetterInfoResponse> getLetterInfo(@PathVariable("letterId") Long letterId) {
        LetterInfoResponse response = letterInfoUseCase.execute(letterId);
        return ResponseEntity.ok().body(response);
    }

    // 편지 상세 조회 (편지 내용)
    @GetMapping("/detail/{letterId}")
    public ResponseEntity<LetterDetailResponse> getLetterDetail(@PathVariable("letterId") Long letterId) {
        LetterDetailResponse response = letterDetailUseCase.execute(letterId);
        return ResponseEntity.ok().body(response);
    }


}
