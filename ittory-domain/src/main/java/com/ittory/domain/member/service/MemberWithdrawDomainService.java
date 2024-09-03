package com.ittory.domain.member.service;

import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.domain.MemberWithdraw;
import com.ittory.domain.member.enums.WithdrawReason;
import com.ittory.domain.member.repository.MemberWithdrawRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberWithdrawDomainService {

    private final MemberWithdrawRepository memberWithdrawRepository;

    @Transactional
    public void saveMemberWithdraw(Member member, WithdrawReason reason, String content) {
        MemberWithdraw memberWithdraw = MemberWithdraw.create(member, reason, content);
        memberWithdrawRepository.save(memberWithdraw);
    }

}
