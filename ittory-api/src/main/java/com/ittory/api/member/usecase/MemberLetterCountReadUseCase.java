package com.ittory.api.member.usecase;

import com.ittory.api.member.dto.MemberLetterCountResponse;
import com.ittory.domain.member.service.LetterBoxDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberLetterCountReadUseCase {

    private final LetterBoxDomainService letterBoxDomainService;

    public MemberLetterCountResponse execute(Long memberId) {
        Integer participationLetterCount = letterBoxDomainService.countParticipationLetterByMemberId(memberId);
        Integer receiveLetterCount = letterBoxDomainService.countReceiveLetterByMemberId(memberId);
        return MemberLetterCountResponse.of(participationLetterCount, receiveLetterCount);
    }
}
