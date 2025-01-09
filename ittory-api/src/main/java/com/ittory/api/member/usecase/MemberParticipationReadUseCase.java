package com.ittory.api.member.usecase;

import com.ittory.api.member.dto.ParticipationResponse;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.member.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberParticipationReadUseCase {

    private final MemberDomainService memberDomainService;

    @Transactional(readOnly = true)
    public ParticipationResponse execute(Long memberId) {
        List<Letter> participatedLetters = memberDomainService.getParticipatedLetters(memberId);
        return ParticipationResponse.of(participatedLetters);
    }
}
