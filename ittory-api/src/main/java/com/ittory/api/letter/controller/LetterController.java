package com.ittory.api.letter.controller;

import com.ittory.api.letter.dto.LetterCreateRequest;
import com.ittory.api.letter.dto.LetterCreateResponse;
import com.ittory.api.letter.usecase.LetterCreateUseCase;
import com.ittory.api.letter.usecase.LetterParticipantReadUseCase;
import com.ittory.api.participant.dto.ParticipantProfile;
import com.ittory.api.participant.dto.ParticipantSortResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/letter")
@RequiredArgsConstructor
public class LetterController {

    private final LetterCreateUseCase letterCreateUseCase;
    private final LetterParticipantReadUseCase letterParticipantReadUseCase;

    @PostMapping
    public ResponseEntity<LetterCreateResponse> createLetter(@RequestBody LetterCreateRequest request) {
        LetterCreateResponse response = letterCreateUseCase.execute(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "현재 유저를 순서에 맞게 조회", description = "순서를 기준으로 오름차순 정렬")
    @GetMapping("/participant/{letterId}")
    public ResponseEntity<ParticipantSortResponse> getParticipantInLetter(@PathVariable Long letterId) {
        List<ParticipantProfile> profiles = letterParticipantReadUseCase.execute(letterId);
        return ResponseEntity.ok().body(ParticipantSortResponse.from(profiles));
    }

}
