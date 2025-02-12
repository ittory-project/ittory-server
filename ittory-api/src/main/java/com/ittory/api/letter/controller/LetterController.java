package com.ittory.api.letter.controller;

import com.ittory.api.letter.dto.*;
import com.ittory.api.letter.usecase.*;
import com.ittory.api.participant.dto.ParticipantNicknameRequest;
import com.ittory.api.participant.dto.ParticipantProfile;
import com.ittory.api.participant.dto.ParticipantSortResponse;
import com.ittory.common.annotation.CurrentMemberId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final LetterEnterUseCase letterEnterUseCase;

    @Operation(summary = "편지 작성", description = "새로운 편지를 작성합니다.")
    @PostMapping
    public ResponseEntity<LetterCreateResponse> createLetter(@RequestBody LetterCreateRequest request) {
        LetterCreateResponse response = letterCreateUseCase.execute(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "편지 반복 횟수 등록", description = "편지의 반복 횟수를 등록합니다.")
    @PostMapping("/repeat-count")
    public ResponseEntity<Void> registerRepeatCount(@RequestBody LetterRepeatCountRequest request) {
        letterRepeatCountUseCase.execute(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "편지 요소 내용 저장", description = "(Authenticated) 사용자가 작성한 내용을 편지 요소에 저장.")
    @PostMapping("/element/{letterElementId}")
    public ResponseEntity<LetterElementResponse> registerLetterElementContent(@PathVariable Long letterElementId,
                                                                              @RequestBody LetterElementRequest request) {
        LetterElementResponse response = letterElementUseCase.execute(letterElementId, request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "편지 삭제", description = "(Authenticated) 편지를 삭제합니다.")
    @DeleteMapping("/{letterId}")
    public ResponseEntity<Void> deleteLetter(@PathVariable Long letterId) {
        letterDeleteUseCase.execute(letterId);
        return ResponseEntity.noContent().build();
    }

    // 편지 기본 정보 조회 (대기실)
    @Operation(summary = "편지 기본 정보 조회", description = "(Authenticated) 대기실에 있는 편지의 기본 정보를 조회합니다.")
    @GetMapping("/info/{letterId}")
    public ResponseEntity<LetterInfoResponse> getLetterInfo(@PathVariable("letterId") Long letterId) {
        LetterInfoResponse response = letterInfoReadUseCase.execute(letterId);
        return ResponseEntity.ok().body(response);
    }

    // 편지 상세 조회 (편지 내용)
    @Operation(summary = "편지 상세 정보 조회", description = "(Authenticated) 편지의 세부 내용을 조회합니다.")
    @GetMapping("/detail/{letterId}")
    public ResponseEntity<LetterDetailResponse> getLetterDetail(@PathVariable("letterId") Long letterId) {
        LetterDetailResponse response = letterDetailReadUseCase.execute(letterId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "현재 유저를 순서에 맞게 조회",
            description = "(Authenticated) order가 sequence면 sequence순서대로 조회. 그 외 혹은 null이면 참여한 순서대로 조회.")
    @GetMapping("/participant/{letterId}")
    public ResponseEntity<ParticipantSortResponse> getParticipantInLetter(@PathVariable Long letterId,
                                                                          @Nullable @RequestParam("order") String order) {
        List<ParticipantProfile> profiles = letterParticipantReadUseCase.execute(letterId, order);
        return ResponseEntity.ok().body(ParticipantSortResponse.from(profiles));
    }

    @Operation(summary = "편지가 저장가능한지 확인", description = "(Authenticated) isStored가 false면 저장가능")
    @GetMapping("/{letterId}/storage-status")
    public ResponseEntity<LetterStorageStatusResponse> getLetterStorageStatus(@PathVariable Long letterId) {
        LetterStorageStatusResponse response = letterStorageStatusCheckUseCase.execute(letterId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "편지를 보관함에 저장", description = "(Authenticated) 받은 편지를 받은 편지함에 저장.")
    @PostMapping("/{letterId}/store")
    public ResponseEntity<Void> getLetterStorageStatus(@CurrentMemberId Long memberId, @PathVariable Long letterId) {
        letterStoreInLetterBoxUseCase.execute(memberId, letterId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "편지에 소속되어 있는 요소 조회", description = "(Authenticated, Pagination) size와 page가 없으면, 편지에 소속한 모든 요소를 조회")
    @GetMapping("/{letterId}/elements")
    public ResponseEntity<LetterElementsResponse> getLetterElements(@PathVariable Long letterId, Pageable pageable) {
        LetterElementsResponse response = letterElementsReadUseCase.execute(letterId, pageable);
        return ResponseEntity.ok().body(response);
    }

    @Deprecated
    @Operation(summary = "편지 참여가능 여부 조회", description = "(Authenticated) 현재 참여중인 참여자가 5명인지 아닌지 획인.")
    @GetMapping("/{letterId}/enter-status")
    public ResponseEntity<LetterEnterStatusResponse> checkLetterEnterStatus(@PathVariable Long letterId) {
        LetterEnterStatusResponse response = letterEnterStatusCheckUseCase.execute(letterId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "편지 시작 시 정보 조회", description = "(Authenticated) 참여인원, 반복횟수, 생성된 요소의 수.")
    @GetMapping("/{letterId}/startInfo")
    public ResponseEntity<LetterStartInfoResponse> getLetterStartInfo(@PathVariable Long letterId) {
        LetterStartInfoResponse response = letterStartInfoReadUseCase.execute(letterId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "편지 참여 요청", description = "(Authenticated) 편지에 참여 성공 시 true, 실패 시 false 반환")
    @PostMapping("/enter/{letterId}")
    public ResponseEntity<LetterEnterStatusResponse> checkLetterEnterStatus(@CurrentMemberId Long memberId,
                                                                            @PathVariable Long letterId,
                                                                            @Nullable @RequestBody ParticipantNicknameRequest request) {
        LetterEnterStatusResponse response = letterEnterUseCase.execute(memberId, letterId, request);
        return ResponseEntity.ok().body(response);
    }

}
