package com.ittory.api.member.usecase;

import com.ittory.api.member.dto.MemberWithdrawRequest;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.MemberDomainService;
import com.ittory.domain.member.service.MemberWithdrawDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWithdrawUseCase {

    private final MemberDomainService memberDomainService;
    private final MemberWithdrawDomainService memberWithdrawDomainService;


    public void execute(Long memberId, MemberWithdrawRequest request) {
        Member member = memberDomainService.findMemberById(memberId);
        memberDomainService.withdrawMember(member);
        memberWithdrawDomainService.saveMemberWithdraw(member, request.getWithdrawReason(), request.getContent());
    }

}
