package com.ittory.api.member.controller;

import com.ittory.api.member.dto.LetterBoxParticipationRequest;
import com.ittory.api.member.usecase.LetterBoxParticipationSaveUseCase;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "참여자들의 편지함에 편지 저장", description = "(Authenticated) " +
            "편지 작성 종료 후, 참여자들의 참여한 편지함에 작성된 편지 저장.")
    @PostMapping("/participation")
    public ResponseEntity<Void> saveParticipationLetterBox(@RequestBody LetterBoxParticipationRequest request) {
        letterBoxParticipationSaveUseCase.execute(request);
        return ResponseEntity.ok().build();
    }

}
