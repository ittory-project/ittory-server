package com.ittory.socket.usecase;

import com.ittory.domain.letter.domain.LetterElement;
import com.ittory.domain.letter.service.LetterElementDomainService;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.MemberDomainService;
import com.ittory.socket.dto.ElementRequest;
import com.ittory.socket.dto.ElementResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LetterWriteUseCase {

    private final MemberDomainService memberDomainService;
    private final LetterElementDomainService letterElementDomainService;

    public ElementResponse execute(Long memberId, ElementRequest request) {
        Member member = memberDomainService.findMemberById(memberId);
        LetterElement element = letterElementDomainService.changeContent(member, request.getElementId(),
                request.getContent());
        return ElementResponse.of(member, element);
    }

}
