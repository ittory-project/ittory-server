package com.ittory.api.member.controller;

import com.ittory.api.member.dto.LetterBoxParticipationRequest;
import com.ittory.api.member.usecase.LetterBoxParticipationSaveUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/letter-box")
@RequiredArgsConstructor
public class LetterBoxController {

    private final LetterBoxParticipationSaveUseCase letterBoxParticipationSaveUseCase;

    @PostMapping("/participation")
    public ResponseEntity<Void> saveParticipationLetterBox(@RequestBody LetterBoxParticipationRequest request) {
        letterBoxParticipationSaveUseCase.execute(request);
        return ResponseEntity.ok().build();
    }

}
