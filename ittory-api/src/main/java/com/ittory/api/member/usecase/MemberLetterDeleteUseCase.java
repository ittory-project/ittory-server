package com.ittory.api.member.usecase;

import com.ittory.domain.member.service.LetterBoxDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberLetterDeleteUseCase {

    private final LetterBoxDomainService letterBoxDomainService;


    public void execute(Long memberId, Long letterId) {
        letterBoxDomainService.deleteByMemberIdAndLetterId(memberId, letterId);
    }
}
