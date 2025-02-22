package com.ittory.api.participant.controller;

import com.ittory.api.participant.dto.*;
import com.ittory.api.participant.service.ParticipantService;
import com.ittory.common.annotation.CurrentMemberId;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participant")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantService participantService;

    @Operation(summary = "참여자 작성 순서 설정", description = "(Authenticated) 랜덤으로 참여자의 작성 순서를 설정.")
    @PostMapping("/random")
    public ResponseEntity<ParticipantSortResponse> setParticipantSortByRandom(@RequestBody SortRandomRequest request) {
        ParticipantSortResponse response = participantService.getParticipantWithRandomSort(request);
        return ResponseEntity.ok().body(response);
    }

    @Deprecated
    @Operation(summary = "사용자 중복 닉네임 확인", description = "(Authenticated) 사용자의 닉네임이 해당 편지에서 중복인지 확인.")
    @GetMapping("/duplicate-nickname")
    public ResponseEntity<NicknameDuplicationResponse> duplicateNickname(
            @RequestParam("letterId") Long letterId, @RequestParam("nickname") String nickname) {
        NicknameDuplicationResponse response = participantService.checkDuplicationNickname(letterId, nickname);
        return ResponseEntity.ok().body(response);
    }

    @Deprecated
    @Operation(summary = "사용자 닉네임 설정", description = "(Authenticated) 사용자가 편지에서 사용할 닉네임 설정.")
    @PostMapping("/nickname/{letterId}")
    public ResponseEntity<ParticipantNicknameResponse> updateNickname(@CurrentMemberId Long memberId,
                                                                      @PathVariable Long letterId,
                                                                      @RequestBody ParticipantNicknameRequest request) {
        ParticipantNicknameResponse response = participantService.updateParticipantNickname(memberId, letterId, request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "사용자 닉네임 삭제", description = "(Authenticated) 사용자가 편지에서 사용할 닉네임을 삭제.")
    @PatchMapping("/nickname/{letterId}")
    public ResponseEntity<Void> deleteNickname(@CurrentMemberId Long memberId,
                                               @PathVariable Long letterId) {
        participantService.deleteParticipantNickname(memberId, letterId);
        return ResponseEntity.ok().build();
    }

}
