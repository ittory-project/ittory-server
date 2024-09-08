package com.ittory.api.letter.controller;

import com.ittory.api.letter.dto.LetterCreateRequest;
import com.ittory.api.letter.dto.LetterCreateResponse;
import com.ittory.api.letter.dto.LetterDetailResponse;
import com.ittory.api.letter.dto.LetterElementRequest;
import com.ittory.api.letter.dto.LetterElementResponse;
import com.ittory.api.letter.dto.LetterElementsResponse;
import com.ittory.api.letter.dto.LetterEnterStatusResponse;
import com.ittory.api.letter.dto.LetterInfoResponse;
import com.ittory.api.letter.dto.LetterRepeatCountRequest;
import com.ittory.api.letter.dto.LetterStartInfoResponse;
import com.ittory.api.letter.dto.LetterStorageStatusResponse;
import com.ittory.api.letter.usecase.LetterCreateUseCase;
import com.ittory.api.letter.usecase.LetterDeleteUseCase;
import com.ittory.api.letter.usecase.LetterDetailReadUseCase;
import com.ittory.api.letter.usecase.LetterElementUseCase;
import com.ittory.api.letter.usecase.LetterElementsReadUseCase;
import com.ittory.api.letter.usecase.LetterEnterStatusCheckUseCase;
import com.ittory.api.letter.usecase.LetterInfoReadUseCase;
import com.ittory.api.letter.usecase.LetterParticipantReadUseCase;
import com.ittory.api.letter.usecase.LetterRepeatCountUseCase;
import com.ittory.api.letter.usecase.LetterStartInfoReadUseCase;
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
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/letter")
@RequiredArgsConstructor
public class LetterController {

    private final LetterCreateUseCase letterCreateUseCase;
    private final LetterDeleteUseCase letterDeleteUseCase;
    private final LetterElementUseCase letterElementUseCase;
    private final LetterRepeatCountUseCase letterRepeatCountUseCase;
    private final LetterInfoReadUseCase letterInfoReadUseCase;
    private final LetterDetailReadUseCase letterDetailReadUseCase;
    private final LetterParticipantReadUseCase letterParticipantReadUseCase;
    private final LetterStorageStatusCheckUseCase letterStorageStatusCheckUseCase;
    private final LetterStoreInLetterBoxUseCase letterStoreInLetterBoxUseCase;
    private final LetterElementsReadUseCase letterElementsReadUseCase;
    private final LetterEnterStatusCheckUseCase letterEnterStatusCheckUseCase;
    private final LetterStartInfoReadUseCase letterStartInfoReadUseCase;

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
    public ResponseEntity<LetterElementResponse> registerLetterElementContent(@PathVariable Long letterElementId,
                                                                              @RequestBody LetterElementRequest request) {
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
        LetterInfoResponse response = letterInfoReadUseCase.execute(letterId);
        return ResponseEntity.ok().body(response);
    }

    // 편지 상세 조회 (편지 내용)
    @GetMapping("/detail/{letterId}")
    public ResponseEntity<LetterDetailResponse> getLetterDetail(@PathVariable("letterId") Long letterId) {
        LetterDetailResponse response = letterDetailReadUseCase.execute(letterId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "현재 유저를 순서에 맞게 조회",
            description = "order가 sequence면 sequence순서대로 조회. 그 외 혹은 null이면 참여한 순서대로 조회.")
    @GetMapping("/participant/{letterId}")
    public ResponseEntity<ParticipantSortResponse> getParticipantInLetter(@PathVariable Long letterId,
                                                                          @Nullable @RequestParam("order") String order) {
        List<ParticipantProfile> profiles = letterParticipantReadUseCase.execute(letterId, order);
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

    @Operation(summary = "편지 참여가능 여부 조회", description = "현재 참여중인 참여자가 5명인지 아닌지 획인")
    @GetMapping("/{letterId}/enter-status")
    public ResponseEntity<LetterEnterStatusResponse> checkLetterEnterStatus(@PathVariable Long letterId) {
        LetterEnterStatusResponse response = letterEnterStatusCheckUseCase.execute(letterId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "편지 시작 시 정보 조회", description = "참여인원, 반복횟수, 생성된 요소의 수")
    @GetMapping("/{letterId}/startInfo")
    public ResponseEntity<LetterStartInfoResponse> getLetterStartInfo(@PathVariable Long letterId) {
        LetterStartInfoResponse response = letterStartInfoReadUseCase.execute(letterId);
        return ResponseEntity.ok().body(response);
    }

}
