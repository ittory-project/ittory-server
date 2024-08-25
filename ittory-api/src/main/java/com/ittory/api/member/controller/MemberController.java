package com.ittory.api.member.controller;

import com.ittory.api.annotation.AuthMember;
import com.ittory.api.member.dto.MemberDetailResponse;
import com.ittory.api.member.dto.MemberSearchResponse;
import com.ittory.api.member.dto.ParticipationResponse;
import com.ittory.api.member.dto.ReceivedLetterResponse;
import com.ittory.api.member.usecase.MemberDetailUseCase;
import com.ittory.api.member.usecase.MemberUserCase;
import com.ittory.api.member.usecase.ParticipationUseCase;
import com.ittory.api.member.usecase.ReceivedLetterUseCase;
import com.ittory.domain.member.domain.Member;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/app/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberUserCase memberUserCase;

    private final MemberDetailUseCase memberDetailUseCase;
    private final ParticipationUseCase participationUseCase;
    private final ReceivedLetterUseCase receivedLetterUseCase;


    @GetMapping("/{memberId}")
    public ResponseEntity<MemberSearchResponse> searchMemberById(@PathVariable("memberId") Long memberId) {
        MemberSearchResponse response = memberUserCase.searchMemberById(memberId);
        return ResponseEntity.ok().body(response);
    }


    @Operation(summary = "마이페이지 정보 조회", description = "사용자의 마이페이지 정보를 조회합니다.")
    @GetMapping("/mypage")
    public ResponseEntity<MemberDetailResponse> getMyPage(@AuthMember Member member) {
        MemberDetailResponse response = memberDetailUseCase.execute(member.getId());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "참여한 편지 조회", description = "참여한 모든 편지를 조회합니다.")
    @GetMapping("/participations/{memberId}")
    public ResponseEntity<ParticipationResponse> getParticipations(@PathVariable @Positive Long memberId) {
        ParticipationResponse response = participationUseCase.execute(memberId);
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "받은 편지함 조회", description = "받은 모든 편지를 조회합니다.")
    @GetMapping("/received/{memberId}")
    public ResponseEntity<ReceivedLetterResponse> getReceivedLetters(@PathVariable @Positive Long memberId) {
        ReceivedLetterResponse response = receivedLetterUseCase.execute(memberId);
        return ResponseEntity.ok().body(response);
    }
}
