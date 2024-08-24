package com.ittory.api.letter.controller;

import com.ittory.api.letter.dto.LetterCreateRequest;
import com.ittory.api.letter.dto.LetterCreateResponse;
import com.ittory.api.letter.dto.LetterElementsResponse;
import com.ittory.api.letter.dto.LetterStorageStatusResponse;
import com.ittory.api.letter.usecase.LetterCreateUseCase;
import com.ittory.api.letter.usecase.LetterElementsReadUseCase;
import com.ittory.api.letter.usecase.LetterParticipantReadUseCase;
import com.ittory.api.letter.usecase.LetterStorageStatusCheckUseCase;
import com.ittory.api.letter.usecase.LetterStoreInLetterBoxUseCase;
import com.ittory.api.participant.dto.ParticipantProfile;
import com.ittory.api.participant.dto.ParticipantSortResponse;
import com.ittory.common.annotation.CurrentMemberId;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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
    private final LetterStorageStatusCheckUseCase letterStorageStatusCheckUseCase;
    private final LetterStoreInLetterBoxUseCase letterStoreInLetterBoxUseCase;
    private final LetterElementsReadUseCase letterElementsReadUseCase;

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

    @Operation(summary = "편지가 저장가능한지 확인", description = "isStored가 false면 저장가능")
    @GetMapping("/{letterId}/storage-status")
    public ResponseEntity<LetterStorageStatusResponse> getLetterStorageStatus(@PathVariable Long letterId) {
        LetterStorageStatusResponse response = letterStorageStatusCheckUseCase.execute(letterId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "편지를 보관함에 저장")
    @PostMapping("/{letterId}/store")
    public ResponseEntity<Void> getLetterStorageStatus(@CurrentMemberId Long memberId, @PathVariable Long letterId) {
        letterStoreInLetterBoxUseCase.execute(memberId, letterId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "편지에 소속되어 있는 요소 조회", description = "size와 page가 없으면, 편지에 소속한 모든 요소를 조회")
    @GetMapping("/{letterId}/elements")
    public ResponseEntity<LetterElementsResponse> getLetterDetails(@PathVariable Long letterId, Pageable pageable) {
        LetterElementsResponse response = letterElementsReadUseCase.execute(letterId, pageable);
        return ResponseEntity.ok().body(response);
    }

}
