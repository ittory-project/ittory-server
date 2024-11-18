package com.ittory.api.member.usecase;

import com.ittory.api.member.dto.MemberAlreadyVisitResponse;
import com.ittory.domain.member.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAlreadyVisitCheckUseCase {

    private final MemberDomainService memberDomainService;


    public MemberAlreadyVisitResponse execute(Long memberId) {
        memberDomainService.findMemberById(memberId);
        Boolean isVisited = memberDomainService.checkVisitedMember(memberId);
        return MemberAlreadyVisitResponse.from(isVisited);
    }

}
