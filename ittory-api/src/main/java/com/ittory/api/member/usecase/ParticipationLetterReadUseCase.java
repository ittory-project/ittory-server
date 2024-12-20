package com.ittory.api.member.usecase;

import com.ittory.api.member.dto.ParticipationResponse;
import com.ittory.domain.member.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipationLetterReadUseCase {

    private final MemberDomainService memberDomainService;

    public ParticipationResponse execute(Long memberId) {
        return ParticipationResponse.of(
                memberDomainService.getParticipatedLetters(memberId)
        );
    }
}
