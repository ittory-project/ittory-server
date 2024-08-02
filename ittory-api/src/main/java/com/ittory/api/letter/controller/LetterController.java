package com.ittory.api.letter.controller;

import com.ittory.api.letter.dto.LetterCreateRequest;
import com.ittory.api.letter.dto.LetterCreateResponse;
import com.ittory.api.letter.usecase.LetterCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/letter")
@RequiredArgsConstructor
public class LetterController {

    private final LetterCreateUseCase letterCreateUseCase;

    @PostMapping
    public ResponseEntity<LetterCreateResponse> createLetter(@RequestBody LetterCreateRequest request) {
        LetterCreateResponse response = letterCreateUseCase.execute(request);
        return ResponseEntity.ok().body(response);
    }
}
