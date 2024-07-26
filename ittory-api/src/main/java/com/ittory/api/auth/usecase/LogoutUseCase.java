package com.ittory.api.auth.usecase;

import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutUseCase {

    private final MemberDomainService memberDomainService;

    public void execute(Member member) {
        memberDomainService.changeRefreshToken(member, null);
    }

}
