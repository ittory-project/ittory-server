package com.ittory.api.letter.usecase;

import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.LetterBoxDomainService;
import com.ittory.domain.member.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterStoreInLetterBoxUseCase {

    private final MemberDomainService memberDomainService;
    private final LetterDomainService letterDomainService;
    private final LetterBoxDomainService letterBoxDomainService;

    public void execute(Long memberId, Long letterId) {
        Member member = memberDomainService.findMemberById(memberId);
        Letter letter = letterDomainService.findLetter(letterId);
        letterBoxDomainService.saveInReceiveLetterBox(member, letter);
    }

}
