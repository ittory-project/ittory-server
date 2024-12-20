package com.ittory.api.member.usecase;

import com.ittory.api.member.dto.ReceivedLetterResponse;
import com.ittory.domain.member.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceivedLetterReadUseCase {

    private final MemberDomainService memberDomainService;

    public ReceivedLetterResponse execute(Long memberId) {
        return ReceivedLetterResponse.of(
                memberDomainService.getReceivedLetters(memberId)
        );
    }
}
