package com.ittory.api.member.usecase;

import com.ittory.api.member.dto.MemberCreateResponse;
import com.ittory.api.member.dto.MemberSearchResponse;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberUserCase {

    private final MemberDomainService memberDomainService;

    public MemberCreateResponse registerMember(String email, String name) {
        Member newMember = memberDomainService.saveMember(email, name);
        return MemberCreateResponse.of(newMember);
    }

    public MemberSearchResponse searchMemberById(Long memberId) {
        Member member = memberDomainService.findMemberById(memberId);
        return MemberSearchResponse.of(member);
    }
}
