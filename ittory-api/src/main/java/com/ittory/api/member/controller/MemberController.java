package com.ittory.api.member.controller;

import com.ittory.api.member.dto.*;
import com.ittory.api.member.usecase.*;
import com.ittory.common.annotation.CurrentMemberId;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberDetailReadUseCase memberDetailReadUseCase;
    private final MemberParticipationReadUseCase memberParticipationReadUseCase;
    private final ReceivedLetterUseCase receivedLetterUseCase;
    private final MemberWithdrawUseCase memberWithdrawUseCase;
    private final MemberLetterCountReadUseCase memberLetterCountReadUseCase;
    private final MemberAlreadyVisitCheckUseCase memberAlreadyVisitCheckUseCase;

    @Operation(summary = "마이페이지 정보 조회", description = "(Authenticated) 사용자의 마이페이지 정보를 조회합니다.")
    @GetMapping("/mypage")
    public ResponseEntity<MemberDetailResponse> getMyPage(@CurrentMemberId Long memberId) {
        MemberDetailResponse response = memberDetailReadUseCase.execute(memberId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "참여한 편지 조회", description = "참여한 모든 편지를 조회합니다.")
    @GetMapping("/participations/{memberId}")
    public ResponseEntity<ParticipationResponse> getParticipations(@PathVariable @Positive Long memberId) {
        ParticipationResponse response = memberParticipationReadUseCase.execute(memberId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "받은 편지함 조회", description = "받은 모든 편지를 조회합니다.")
    @GetMapping("/received/{memberId}")
    public ResponseEntity<ReceivedLetterResponse> getReceivedLetters(@PathVariable @Positive Long memberId) {
        ReceivedLetterResponse response = receivedLetterUseCase.execute(memberId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "회원 탈퇴", description = "(Authenticated) " +
            "탈퇴사유 = [ERROR, ANOTHER_ACCOUNT, BETTER_FUN, NOT_USE, ETC")
    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdrawMemberById(@CurrentMemberId Long memberId,
                                                   @RequestBody MemberWithdrawRequest request) {
        memberWithdrawUseCase.execute(memberId, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "사용자 각 편지함의 편지 수 조회", description = "(Authenticated) 받은, 참여한 편지 수 조회.")
    @GetMapping("/letter-counts")
    public ResponseEntity<MemberLetterCountResponse> getMemberLetterCounts(@CurrentMemberId Long memberId) {
        MemberLetterCountResponse response = memberLetterCountReadUseCase.execute(memberId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "재방문 사용자인지 확인", description = "(Authenticated) 참여한 편지를 기준으로 재방문 사용자인지 확인.")
    @GetMapping("/visit")
    public ResponseEntity<MemberAlreadyVisitResponse> checkMemberAlreadyVisit(@CurrentMemberId Long memberId) {
        MemberAlreadyVisitResponse response = memberAlreadyVisitCheckUseCase.execute(memberId);
        return ResponseEntity.ok().body(response);
    }

}
