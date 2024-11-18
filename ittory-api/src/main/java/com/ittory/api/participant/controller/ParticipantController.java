package com.ittory.api.participant.controller;

import com.ittory.api.participant.dto.NicknameDuplicationResponse;
import com.ittory.api.participant.dto.ParticipantSortResponse;
import com.ittory.api.participant.dto.SortRandomRequest;
import com.ittory.api.participant.usecase.ParticipantNickNameCheckUseCase;
import com.ittory.api.participant.usecase.ParticipantSetSortUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/participant")
@RequiredArgsConstructor
public class ParticipantController {

    private final ParticipantSetSortUseCase participantSortSetUseCase;
    private final ParticipantNickNameCheckUseCase participantNickNameCheckUseCase;

    @Operation(summary = "참여자 작성 순서 설정", description = "(Authenticated) 랜덤으로 참여자의 작성 순서를 설정.")
    @PostMapping("/random")
    public ResponseEntity<ParticipantSortResponse> setParticipantSortByRandom(@RequestBody SortRandomRequest request) {
        ParticipantSortResponse response = participantSortSetUseCase.execute(request);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "사용자 중복 닉네임 확인", description = "(Authenticated) 사용자의 닉네임이 해당 편지에서 중복인지 확인.")
    @GetMapping("/duplicate-nickname")
    public ResponseEntity<NicknameDuplicationResponse> duplicateNickname(
            @RequestParam("letterId") Long letterId, @RequestParam("nickname") String nickname) {
        NicknameDuplicationResponse response = participantNickNameCheckUseCase.execute(letterId, nickname);
        return ResponseEntity.ok().body(response);
    }

}
