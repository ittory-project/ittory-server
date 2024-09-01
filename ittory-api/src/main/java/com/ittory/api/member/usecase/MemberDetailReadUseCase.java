package com.ittory.api.member.usecase;

import com.ittory.api.member.dto.MemberDetailResponse;
import com.ittory.domain.member.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailReadUseCase {

    private final MemberDomainService memberDomainService;

    public MemberDetailResponse execute(Long memberId) {
        return MemberDetailResponse.from(memberDomainService.findMemberById(memberId));
    }
}
